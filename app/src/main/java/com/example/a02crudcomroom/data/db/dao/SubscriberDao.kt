package com.example.a02crudcomroom.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.a02crudcomroom.data.db.entity.SubscriberEntity

@Dao
interface SubscriberDao {

    @Insert
    suspend fun insert(subscriber: SubscriberEntity): Long

    @Update
    suspend fun update(subscriber: SubscriberEntity)

    @Query("DELETE FROM subscriber WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("DELETE FROM subscriber")
    suspend fun deleteAll()

    @Query("SELECT * FROM subscriber")
    //aqui é possível retornar diretamente um liveData pq importamos a biblioteca ktx do room(kotlin extensions) e portanto
    //toda vez que for inserido ou atualizado alguma coisa no banco de dados automaticamente será disparado o evento e quem estiver
    //escutando o liveData vai reagir a esse evento e vai atualizar na tela pro usuário de forma automática
    //A função não precisa ser suspend quando retorna um LiveData
//    fun getAll(): LiveData<List<SubscriberEntity>>
    suspend fun getAll(): List<SubscriberEntity>
}