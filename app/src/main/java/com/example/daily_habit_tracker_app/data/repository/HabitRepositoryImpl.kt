package com.example.daily_habit_tracker_app.data.repository


import com.example.daily_habit_tracker_app.domain.model.Habit
import com.example.daily_habit_tracker_app.domain.repository.HabitRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HabitRepositoryImpl @Inject constructor() : HabitRepository {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private fun userHabitsCollection() = db.collection("users")
        .document(auth.currentUser?.uid ?: "")
        .collection("habits")

    override fun getHabits(): Flow<List<Habit>> = callbackFlow {
        val listenerRegistration: ListenerRegistration = userHabitsCollection()
            .addSnapshotListener { snapshot, error ->
                if (error != null || snapshot == null) {
                    return@addSnapshotListener
                }
                val habits = snapshot.documents.mapNotNull {
                    it.toObject(Habit::class.java)?.copy(id = it.id)
                }
                trySend(habits).isSuccess
            }

        awaitClose { listenerRegistration.remove() }
    }

    override suspend fun getHabitById(id: String): Habit? {
        val doc = userHabitsCollection().document(id).get().await()
        return doc.toObject(Habit::class.java)?.copy(id = doc.id)
    }

    override suspend fun insertHabit(habit: Habit) {
        val habitToSave = habit.copy(id = "")
        val docRef = userHabitsCollection().add(habitToSave).await()
        docRef.update("id", docRef.id)
    }

    override suspend fun updateHabit(habit: Habit) {
        userHabitsCollection().document(habit.id).set(habit).await()
    }

    override suspend fun deleteHabit(habit: Habit) {
        userHabitsCollection().document(habit.id).delete().await()
    }
}
