package edu.davidmarhuenda.mywonderfulmoneycontrol_v2.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import edu.davidmarhuenda.mywonderfulmoneycontrol_v2.model.Categories
import edu.davidmarhuenda.mywonderfulmoneycontrol_v2.model.Movements
import java.util.ArrayList

class DBOpenHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION){

    val TAG = "SQLite"
    companion object {

        val DATABASE_VERSION = 1
        val DATABASE_NAME = "mwmc.db"

        val TABLA_REGISTROS  = "movements"
        val COLUMNA_REGID = "registroId"
        val COLUMNA_TITLE = "title"
        val COLUMNA_DATE = "date"
        val COLUMNA_TYPE = "type"
        val COLUMNA_CATEGORY = "category"
        val COLUMNA_AMOUNT = "amount"
        val COLUMNA_TICKET = "imgUrl"

        val TABLA_CATEGORIES = "categories"
        val COLUMNA_CATEGID = "categoryId"
        val COLUMNA_CATEGORYNAME = "category"
    }

    /**
     * Método llamado cuando se crea la BD por primera vez
     */
    override fun onCreate(db: SQLiteDatabase?) {
        try {
            val crearTablaCategorias = "CREATE TABLE $TABLA_CATEGORIES " +
                    "($COLUMNA_CATEGID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUMNA_CATEGORYNAME TEXT)"
            db!!.execSQL(crearTablaCategorias)

            val crearTablaRegistros = "CREATE TABLE $TABLA_REGISTROS " +
                    "($COLUMNA_REGID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUMNA_TITLE TEXT, " + "$COLUMNA_DATE TEXT, " +
                    "$COLUMNA_TYPE TEXT, " + "$COLUMNA_CATEGORY TEXT, " +
                    "$COLUMNA_AMOUNT REAL, " + "$COLUMNA_TICKET TEXT), " +
                    "FOREING KEY ($COLUMNA_CATEGORY) REFERENCES $TABLA_CATEGORIES($COLUMNA_CATEGORYNAME) " +
                    "ON UPDDATE CASCADE"
            db!!.execSQL(crearTablaRegistros)
        } catch (e: SQLiteException) {
            Log.e("$TAG (onCreate)", e.message.toString())
        }
    }

    /**
     * Este método se invocará cuando la base de datos necesite ser actualizada.
     * En esta práctica no se utilizará
     */
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        try {
            val dropTablaRegistros = "DROP TABLE IF EXISTS $TABLA_CATEGORIES"
            db!!.execSQL(dropTablaRegistros)
            onCreate(db)
        } catch (e: SQLiteException) {
            Log.e("$TAG (onUpgrade)", e.message.toString())
        }
    }

    /**
     * Método opcional. Se llamará a este método después de abrir la base de
     * datos, antes de ello, comprobará si está en modo lectura. Se llama justo
     * después de establecer la conexión y crear el esquema.
     */
    override fun onOpen(db: SQLiteDatabase?) {
        super.onOpen(db)
        Log.d("$TAG (onOpen)", "¡¡Base de datos abierta!!")
    }

    /**
     * Método para añadir un registro a la BD
     */
    fun addRegistro(datos: Movements) {
        // Se crea un ArrayMap<>() haciendo uso de ContentValues().
        val data = ContentValues()
        data.put(COLUMNA_REGID, datos.title)
        data.put(COLUMNA_TITLE, datos.date)
        data.put(COLUMNA_DATE,datos.type)
        data.put(COLUMNA_TYPE,  datos.category)
        data.put(COLUMNA_CATEGORY, datos.amount)
        data.put(COLUMNA_AMOUNT , datos.imgUrl)

        // Se abre la BD en modo escritura.
        val db = this.writableDatabase
        db.insert(TABLA_REGISTROS, null, data)
        db.close()
    }

    /**
     * Método para añadir una categoría a la BD
     */
    fun addCategory(newCategory: String) {
        // Se crea un ArrayMap<>() haciendo uso de ContentValues().
        val data = ContentValues()
        data.put(COLUMNA_CATEGORYNAME, newCategory)

        // Se abre la BD en modo escritura.
        val db = this.writableDatabase
        db.insert(TABLA_CATEGORIES, null, data)
        db.close()
    }

    /**
     * Método para eliminar un registro de la tabla por el identificador.
     */
    fun delRegistro(identifier: Int): Int {
        val args = arrayOf(identifier.toString())

        // Se abre la BD en modo escritura.
        val db = this.writableDatabase

        // Se elimina el registro
        val result = db.delete(TABLA_REGISTROS, "${COLUMNA_REGID}ID = ?", args)

        db.close()
        return result
    }

    /**
     * Método para eliminar una categoría de la tabla por el identificador.
     */
    fun deleteCategory(identifier: Int): Int {
        val args = arrayOf(identifier.toString())

        // Se abre la BD en modo escritura.
        val db = this.writableDatabase

        // Se elimina la categoría
        val result = db.delete(TABLA_CATEGORIES, "${COLUMNA_CATEGORYNAME}ID = ?", args)

        db.close()
        return result
    }

    /**
     * Método para guardar los datos de la tabla en un ArrayList
     */
    fun getDBRegistros(context: Context): MutableList<Movements>{

        val lista: MutableList<Movements> = ArrayList()
        // Se abre la BD en modo lectura.
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery(
            "SELECT * FROM ${DBOpenHelper.TABLA_REGISTROS};", null)

        try{
            if (cursor != null && cursor.count > 0){
                cursor.moveToFirst()

                do{
                    val dt = Movements()
                    //la posición cero es el id autoincremental
                    dt.registroId = cursor.getInt(0)
                    dt.title = cursor.getString(1)
                    dt.date = cursor.getString(2)
                    dt.type = cursor.getString(3)
                    dt.category = cursor.getString(4)
                    dt.amount = cursor.getDouble(5)
                    dt.imgUrl = cursor.getString(6)
                    lista.add(dt)
                }while(cursor.moveToNext())
            }
        }catch (e: SQLiteException){
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
        }

        db.close()
        return lista
    }

    /**
     * Método para guardar las categorías en un ArrayList
     */
    fun getDBCategories(context: Context): MutableList<Categories>{

        val lista: MutableList<Categories> = ArrayList()
        // Se abre la BD en modo lectura.
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery(
            "SELECT * FROM ${DBOpenHelper.TABLA_CATEGORIES};", null)

        try{
            if (cursor != null && cursor.count > 0){
                cursor.moveToFirst()

                do{
                    val dt = Categories()
                    //la posición cero es el id autoincremental
                    dt.categoryId = cursor.getInt(0)
                    dt.category = cursor.getString(1)
                    lista.add(dt)
                }while(cursor.moveToNext())
            }
        }catch (e: SQLiteException){
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
        }

        db.close()
        return lista
    }
}