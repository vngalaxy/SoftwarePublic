package vn.vngalaxy.fas.model.base

import io.appwrite.models.Document

abstract class BaseResponse<T, D> {
    abstract fun fromDocument(document: Document<D>): T

    fun fromDocumentList(documents: List<Document<D>>): List<T> {
        return documents.map { fromDocument(it) }
    }

    abstract fun fromMap(data: Map<String, Any>): T
}