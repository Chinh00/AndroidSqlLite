package com.superman.androidsqllite

import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.superman.androidsqllite.database.DatabaseInstance

class MainActivity : ComponentActivity() {
    private val DatabaseInstance = DatabaseInstance(this);


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()



        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                MainScreen(onCreate = {
                    if (Validate(it)) {
                        if (FindById(it.maLop).maLop != "-1") {
                            Toast.makeText(this, "Đã tồn tại giá trị ", Toast.LENGTH_LONG).show()

                        } else {
                            val dbContext = DatabaseInstance.writableDatabase
                            dbContext.beginTransaction()
                            val contentValues = ContentValues().apply {
                                put(com.superman.androidsqllite.database.DatabaseInstance.MA_LOP, it.maLop)
                                put(com.superman.androidsqllite.database.DatabaseInstance.TEN_LOP, it.tenLop)
                                put(com.superman.androidsqllite.database.DatabaseInstance.SI_SO, it.siSo)
                            }
                            dbContext.insert(com.superman.androidsqllite.database.DatabaseInstance.TABLE_NAME, null, contentValues)
                            dbContext.setTransactionSuccessful()
                            Toast.makeText(this, "Thêm mới thành công", Toast.LENGTH_LONG).show()
                            dbContext.endTransaction()
                        }

                    }
                }, onUpdate = {
                    if (Validate(it)) {
                        if (FindById(it.maLop).maLop != "-1")
                        {
                            val dbContext = DatabaseInstance.writableDatabase
                            dbContext.beginTransaction()
                            val contentValues = ContentValues().apply {
                                put(com.superman.androidsqllite.database.DatabaseInstance.TEN_LOP, it.tenLop)
                                put(com.superman.androidsqllite.database.DatabaseInstance.SI_SO, it.siSo)
                            }
                            dbContext.update(com.superman.androidsqllite.database.DatabaseInstance.TABLE_NAME, contentValues, "${com.superman.androidsqllite.database.DatabaseInstance.MA_LOP} = ?", arrayOf(it.maLop.toString()))
                            dbContext.setTransactionSuccessful()
                            Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_LONG).show()
                            dbContext.endTransaction()
                        } else {
                            Toast.makeText(this, "Không tồn tại ", Toast.LENGTH_LONG).show()
                        }

                    }

                }, onDelete = {

                    if (Validate(it)) {
                        if (FindById(it.maLop).maLop != "-1") {
                            val dbContext = DatabaseInstance.writableDatabase
                            dbContext.beginTransaction()
                            val contentValues = ContentValues().apply {
                                put(com.superman.androidsqllite.database.DatabaseInstance.TEN_LOP, it.tenLop)
                                put(com.superman.androidsqllite.database.DatabaseInstance.SI_SO, it.siSo)
                            }
                            dbContext.delete(com.superman.androidsqllite.database.DatabaseInstance.TABLE_NAME, "${com.superman.androidsqllite.database.DatabaseInstance.MA_LOP} = ?", arrayOf(it.maLop.toString()))
                            dbContext.setTransactionSuccessful()
                            Toast.makeText(this, "Xóa thành công", Toast.LENGTH_LONG).show()
                            dbContext.endTransaction()
                        } else {
                            Toast.makeText(this, "Không tồn tại", Toast.LENGTH_LONG).show()

                        }

                    }
                }, onQuery = {
                    val intent = Intent(this, ListActivity::class.java)
                    startActivity(intent)
                })

            }
        }
    }
    fun Validate(lop: Lop): Boolean {
        if (lop.maLop.isEmpty() || lop.tenLop.isEmpty() || lop.siSo.isEmpty()) {
            Toast.makeText(this, "Nhập rỗng ", Toast.LENGTH_LONG).show()
            return false
        }
        val ss = lop.siSo.toIntOrNull()
        if (ss == null) {
            Toast.makeText(this, "Sĩ số không phải số  ", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }
    fun FindById(id: String): Lop {
        val db = DatabaseInstance.readableDatabase
        val cursor: Cursor = db.query(
            com.superman.androidsqllite.database.DatabaseInstance.TABLE_NAME,
            arrayOf(com.superman.androidsqllite.database.DatabaseInstance.MA_LOP, com.superman.androidsqllite.database.DatabaseInstance.TEN_LOP, com.superman.androidsqllite.database.DatabaseInstance.SI_SO),
            "${com.superman.androidsqllite.database.DatabaseInstance.MA_LOP} = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )
        if (cursor.moveToFirst()) {
                val tenlop = cursor.getString(cursor.getColumnIndexOrThrow(com.superman.androidsqllite.database.DatabaseInstance.TEN_LOP))
                val siso = cursor.getInt(cursor.getColumnIndexOrThrow(com.superman.androidsqllite.database.DatabaseInstance.SI_SO))
            return Lop(id, tenlop, siso.toString())
        }
        return Lop("-1", "", "")
    }

}



data class Lop(var maLop: String, var tenLop: String, var siSo: String)

@Preview
@Composable
fun MainScreen(onCreate: (Lop) -> Unit, onUpdate: (Lop) -> Unit, onDelete: (Lop) -> Unit, onQuery: () -> Unit) {
    var malop by remember { mutableStateOf("") }
    var tenlop by remember { mutableStateOf("") }
    var siso by remember { mutableStateOf("") }




    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Greeting("QUẢN LÝ SINH VIÊN")
        Column(modifier = Modifier.padding(20.dp)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Mã lớp", modifier = Modifier.width(100.dp), fontSize = 20.sp)
                TextField(
                    value = malop,
                    onValueChange = {
                        malop = it
                    },
                    textStyle = TextStyle(fontSize = 18.sp),
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Tên lớp", modifier = Modifier.width(100.dp), fontSize = 20.sp)
                TextField(
                    value = tenlop,
                    onValueChange = {
                        tenlop = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(fontSize = 18.sp),
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Sĩ số", modifier = Modifier.width(100.dp), fontSize = 20.sp)
                TextField(
                    value = siso,
                    onValueChange = {
                        siso = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(fontSize = 18.sp),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row (modifier = Modifier.fillMaxWidth()) {
                Button(onClick = {
                                 onCreate(Lop(malop, tenlop, siso))
                }, modifier = Modifier.weight(1f)) {
                    Text(text = "INSERT")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { onDelete(Lop(malop, tenlop, siSo = siso)) }, modifier = Modifier.weight(1f)) {
                    Text(text = "DELETE")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Button(onClick = { onUpdate(Lop(malop, tenlop, siSo = siso)) }, modifier = Modifier.weight(1f)) {
                    Text(text = "UPDATE")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { onQuery() }, modifier = Modifier.weight(1f)) {
                    Text(text = "QUERY")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

}