package com.example.littlelemon

import androidx.lifecycle.LiveData
import androidx.room.*

@Database(entities = [MenuItemRoom::class], version = 1)
abstract class MenuDatabase: RoomDatabase() {
    abstract fun MenuDao(): MenuDao
}


@Entity
data class MenuItemRoom (
    @PrimaryKey val id:Int,
    val title: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val category: String
        )

@Dao
interface MenuDao {
    @Query("SELECT * FROM MenuItemRoom")
    fun getAllMenuItems(): LiveData<List<MenuItemRoom>>

    @Insert
    fun saveMenuItem( vararg menuItem: MenuItemRoom)

     @Delete
     fun deleteMenuItem(menuItem: MenuItemRoom)

     @Query("SELECT(SELECT COUNT(*) FROM MenuItemRoom) == 0")
     fun isEmpty(): Boolean
}




