package com.superman.androidsqllite

import android.database.Cursor
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.superman.androidsqllite.database.DatabaseInstance

class ListActivity : ComponentActivity() {
    private val DatabaseInstance = DatabaseInstance(this);
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        var array = ArrayList<Lop>()
        val db = DatabaseInstance.readableDatabase
        val cursor: Cursor = db.query(
            com.superman.androidsqllite.database.DatabaseInstance.TABLE_NAME,
            arrayOf(com.superman.androidsqllite.database.DatabaseInstance.MA_LOP, com.superman.androidsqllite.database.DatabaseInstance.TEN_LOP, com.superman.androidsqllite.database.DatabaseInstance.SI_SO),
            null,
            null,
            null,
            null,
            null
        )
        if (cursor.moveToFirst()) {
            val id = cursor.getString(cursor.getColumnIndexOrThrow(com.superman.androidsqllite.database.DatabaseInstance.MA_LOP))
            val tenlop = cursor.getString(cursor.getColumnIndexOrThrow(com.superman.androidsqllite.database.DatabaseInstance.TEN_LOP))
            val siso = cursor.getInt(cursor.getColumnIndexOrThrow(com.superman.androidsqllite.database.DatabaseInstance.SI_SO))
            array.add(Lop(id, tenlop, siso.toString()))
        }
        setContent {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                contentAlignment = Alignment.Center) {
                MyListView(array)
            }
        }
    }
}


@Composable
fun MyListView(itemList: ArrayList<Lop>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        itemList.forEach { item -> @androidx.compose.runtime.Composable {
            ListItemComponent(item = item)
        } }
    }
}

@Composable
fun ListItemComponent(item: Lop) {
    Row {
        Text(
            text = item.maLop,
            fontSize = 20.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
    Row {
        Text(
            text = item.tenLop,
            fontSize = 20.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
    Row {
        Text(
            text = item.siSo,
            fontSize = 20.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }

}
