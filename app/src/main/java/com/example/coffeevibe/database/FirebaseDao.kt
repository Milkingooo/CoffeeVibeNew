package com.example.coffeevibe.database

interface FirebaseDaoInterface {
    fun getAllPositions()
    fun addOrder()
    fun getUserId()
}

class FirebaseDao() : FirebaseDaoInterface {
    private val db = Firestore.getInstance()

    override fun getAllPositions() {
        db.collection("menu")
            .get()
        .addOnSuccessListener { result ->
            TODO("Not yet implemented")
        }
        .addOnFailureListener { exception ->
            TODO("Not yet implemented")
        }

    }

    override fun addOrder() {
//        db.collection("menu")
//            .add()
//            .addOnSuccessListener { result ->
//                TODO("Not yet implemented")
//            }
//        .addOnFailureListener { exception ->
//            TODO("Not yet implemented")
//        }
    }

    override fun getUserId() {
        TODO("Not yet implemented")
    }

}