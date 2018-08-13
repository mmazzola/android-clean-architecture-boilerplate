package org.buffer.android.boilerplate.data.mapper

/**
 * Interface for model mappers. It provides helper methods that facilitate
 * retrieving of models from outer data source layers
 *
 * @param <E> the entity in the Data Layer
 * @param <D> the remote model in the Domain Layer
 */
interface Mapper<E, D> {

    fun mapFromEntity(type: E): D

    fun mapToEntity(type: D): E

}