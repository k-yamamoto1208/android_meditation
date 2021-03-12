package com.example.meditationapp.repository

import com.example.meditationapp.db.History
import com.example.meditationapp.db.HistoryDao
import javax.inject.Inject

class HistoryRepository @Inject constructor(private val historyDao: HistoryDao) {

    fun createHistoryInDB(history: History) = historyDao.create(history)

    fun updateHistoryInDB(id: Int, memo: String) = historyDao.update(id, memo)

    fun findAllHistoryInDB() = historyDao.findAll()
}