package org.d3if4097.mobpro2.data

import androidx.lifecycle.LiveData
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.getValue

class MahasiswaLiveData(
    private val db: DatabaseReference
) : LiveData<List<Mahasiswa>>() {
    private val data = ArrayList<Mahasiswa>()
    init {
        value = data.toList()
    }

    private val listener = object : ChildEventListener {
        // Dua method di bawah ini tidak kita gunakan
        override fun onCancelled(error: DatabaseError) { }
        override fun onChildMoved(snapshot: DataSnapshot, name: String?) { }
        // Tiga method di bawah ini akan kita isi nanti
        override fun onChildChanged(snapshot: DataSnapshot, name: String?) {
            val mahasiswa = snapshot.getValue<Mahasiswa>() ?: return
            mahasiswa.id = snapshot.key ?: return
            val pos = data.indexOfFirst { it.id == mahasiswa.id }
            data[pos] = mahasiswa
            value = data.toList()
        }
        override fun onChildAdded(snapshot: DataSnapshot, name: String?) {
            val mahasiswa = snapshot.getValue<Mahasiswa>() ?: return
            mahasiswa.id = snapshot.key ?: return
            data.add(mahasiswa)
            value = data.toList()
        }
        override fun onChildRemoved(snapshot: DataSnapshot) {
            val id = snapshot.key ?: return
            val pos = data.indexOfFirst { it.id == id }
            data.removeAt(pos)
            value = data.toList()
        }
    }
    override fun onActive() {
        db.addChildEventListener(listener)
    }
    override fun onInactive() {
        db.removeEventListener(listener)
        data.clear()
    }
}