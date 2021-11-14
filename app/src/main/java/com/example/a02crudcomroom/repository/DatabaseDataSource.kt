package com.example.a02crudcomroom.repository

import androidx.lifecycle.LiveData
import com.example.a02crudcomroom.data.db.dao.SubscriberDao
import com.example.a02crudcomroom.data.db.entity.SubscriberEntity

class DatabaseDataSource(private val subscriberDao: SubscriberDao) : SubscriberRepository {

    override suspend fun insertSubscriber(name: String, email: String): Long {
        return subscriberDao.insert(SubscriberEntity(name = name, email = email))
    }

    override suspend fun updateSubscriber(id: Long, name: String, email: String) {
        subscriberDao.update(SubscriberEntity(id = id, name = name, email = email))
    }

    override suspend fun deleteSubscriber(id: Long) {
        subscriberDao.delete(id = id)
    }

    override suspend fun deleteAllSubscriber() {
        subscriberDao.deleteAll()
    }

//    override fun getAllSubscriber(): LiveData<List<SubscriberEntity>> {
    override suspend fun getAllSubscriber(): List<SubscriberEntity> {
        return subscriberDao.getAll()
    }
}