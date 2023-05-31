package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var count = 0
        val database = Firebase.database
        val myRef = database.getReference()

        val save = findViewById<Button>(R.id.save)
        val getData = findViewById<Button>(R.id.getData)
        val personNameEditText = findViewById<EditText>(R.id.PersonName)
        val personIDEditText = findViewById<EditText>(R.id.PersonID)
        val personAgeEditText = findViewById<EditText>(R.id.PersonAge)
        val textData = findViewById<TextView>(R.id.textData) // تعريف العنصر TextView المسمى textData

        save.setOnClickListener {
            val name = personNameEditText.text.toString()
            val id = personIDEditText.text.toString()
            val age = personAgeEditText.text.toString()

            val person = hashMapOf(
                "name" to name,
                "id" to id,
                "age" to age
            )

            myRef.child("person").child("$count").setValue(person)
            count++
            Toast.makeText(applicationContext, "Success", Toast.LENGTH_SHORT).show()
        }

        getData.setOnClickListener {
            // Read from the database
            myRef.addValueEventListener(object: ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    val value = snapshot.getValue()
                    textData.text = value.toString()
                    Toast.makeText(applicationContext, "Success", Toast.LENGTH_LONG).show()

                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(applicationContext, "Failed", Toast.LENGTH_LONG).show()

                }
            })

        }
    }
}
