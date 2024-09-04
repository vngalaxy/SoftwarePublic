package vn.vngalaxy.fas.presentation.views.sensor

import android.graphics.Color
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import androidx.navigation.navOptions
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import org.koin.androidx.viewmodel.ext.android.viewModel
import vn.vngalaxy.fas.R
import vn.vngalaxy.fas.databinding.FragmentSensorDetailBinding
import vn.vngalaxy.fas.model.SensorStatus
import vn.vngalaxy.fas.model.SensorType
import vn.vngalaxy.fas.presentation.base.BaseFragment
import vn.vngalaxy.fas.shared.extensions.navigate
import vn.vngalaxy.fas.shared.extensions.setOnClick


class SensorDetailFragment : BaseFragment<FragmentSensorDetailBinding, SensorDetailViewModel>() {
    override val viewModel: SensorDetailViewModel by viewModel()
    private val args: SensorDetailFragmentArgs by navArgs()

    override fun inflateViewBinding(inflater: LayoutInflater) = FragmentSensorDetailBinding.inflate(inflater)

    override fun onStart() {
        super.onStart()
        args.sensor.room?.let { viewModel.loadRoomsDetail(it) }
    }

    override fun setUpView() {
        with(viewBinding) {
            sensorChart.description.isEnabled = false
            // set an alternative background color
            sensorChart.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))

            val data = LineData()
            data.setValueTextColor(R.color.text_primary)

            // add empty data
            sensorChart.data = data

            val leftAxis = sensorChart.axisLeft
            leftAxis.axisMaximum = 100f
            leftAxis.axisMinimum = 0f
            leftAxis.setDrawGridLines(true)

            sensorChart.axisRight.isEnabled = false
            sensorChart.legend.isEnabled = false
            sensorChart.xAxis.isEnabled = false
        }
    }

    override fun bindView() {
        with(viewBinding) {
            sensorNameTxt.text = args.sensor.name
            sensorChartTxt.text = getString(R.string.sensor_chart_with_type, SensorType.fromValue(args.sensor.type).viValue)

            //Circular Progress View
            bindCircularProgress(args.sensor.value.toInt(), SensorType.maxValue(args.sensor.type.toString()))
            viewModel.loadSensorDataRealtime(args.sensor)
            sensorChart.axisLeft.axisMaximum = SensorType.maxValue(args.sensor.type.toString()).toFloat()
            bindStatusText()
        }
    }

    override fun registerLiveData() {
        super.registerLiveData()

        with(viewModel) {
            realTimeSensor.observe(viewLifecycleOwner) {
                bindCircularProgress(it.value.toInt(), SensorType.maxValue(it.type.toString()))
                addEntry(it.value.toFloat())
            }
        }
    }

    override fun handleEvent() {
        with(viewBinding) {
            btnBack.setOnClick {
                val action = SensorDetailFragmentDirections.actionToSensor()
                navigate(action, navOptions { popUpTo(R.id.sensor_detail) { inclusive = true } })
            }

            btnEditSensor.setOnClick {
                if (viewModel.room.value != null) {
                    navigate(SensorDetailFragmentDirections.actionToEditSensor(args.sensor.copy(room = viewModel.room.value)))
                }
            }
        }
    }

    private fun bindCircularProgress(currentValue: Int, maxValue: Int) {
        with(viewBinding) {
            valueProgress.setTotal(maxValue)
            valueProgress.setProgress(currentValue)
        }
    }

    private fun bindStatusText() {
        with(viewBinding) {
            val normalColor = requireContext().getColor(R.color.primary)
            val dangerColor = requireContext().getColor(R.color.text_red)
            val warningColor = requireContext().getColor(R.color.text_warning)

            when (args.sensor.status) {
                SensorStatus.ON.value -> {
                    sensorStatusTxt.text = requireContext().getString(R.string.normal)
                    sensorStatusTxt.setTextColor(normalColor)
                }

                SensorStatus.OFF.value -> {
                    sensorStatusTxt.text = requireContext().getString(R.string.off)
                    sensorStatusTxt.setTextColor(dangerColor)
                }

                SensorStatus.WARNING.value -> {
                    sensorStatusTxt.text = requireContext().getString(R.string.warning)
                    sensorStatusTxt.setTextColor(warningColor)
                }

                SensorStatus.FIRE.value -> {
                    sensorStatusTxt.text = requireContext().getString(R.string.fire)
                    sensorStatusTxt.setTextColor(dangerColor)
                }

                else -> {
                    sensorStatusTxt.text = requireContext().getString(R.string.unknown)
                    sensorStatusTxt.setTextColor(warningColor)
                }
            }
        }
    }

    private fun addEntry(value: Float) {
        val data = viewBinding.sensorChart.data

        if (data != null) {
            var set = data.getDataSetByIndex(0)

            if (set == null) {
                set = createSet()
                data.addDataSet(set)
            }

            data.addEntry(Entry(set.entryCount.toFloat(), value), 0)
            data.notifyDataChanged()

            // let the chart know it's data has changed
            viewBinding.sensorChart.notifyDataSetChanged()

            // limit the number of visible entries
            viewBinding.sensorChart.setVisibleXRangeMaximum(60f)

            // move to the latest entry
            viewBinding.sensorChart.moveViewToX(data.entryCount.toFloat())
        }
    }

    private fun createSet(): LineDataSet {
        val set = LineDataSet(null, null).apply {
            mode = LineDataSet.Mode.CUBIC_BEZIER
            axisDependency = YAxis.AxisDependency.LEFT
            color = Color.rgb(52, 224, 161)
            setCircleColor(Color.WHITE)
            lineWidth = 2f
            fillAlpha = 255
            fillColor = Color.rgb(52, 224, 161)
            valueTextColor = Color.WHITE
            setDrawValues(false)
        }
        set.setDrawFilled(true)
        set.setDrawCircles(false)
        return set
    }
}