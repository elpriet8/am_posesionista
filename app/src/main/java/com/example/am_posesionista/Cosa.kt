package com.example.am_posesionista

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import java.util.*

private const val TAG = "debug"
class Cosa(): Parcelable {
    var nombreCosa: String = ""
    var valorPesos: Int = 0
    //var serialnum: String = UUID.randomUUID().toString().substring(0,6)
    var serialnum: String = ""
    var fechaCreacion: Date = Date()
    var idCosa = UUID.randomUUID().toString().substring(0,6)

    // Esto se lee en orden como se escriben en write to Parcel
    constructor(parcel: Parcel): this(){
        Log.d(TAG, "constructor")
        nombreCosa = parcel.readString().toString()
        valorPesos = parcel.readInt()
        serialnum = parcel.readString().toString()
        fechaCreacion = parcel.readSerializable() as Date
        idCosa = parcel.readString().toString()
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        Log.d(TAG, "write to parcel")

        dest.writeString(nombreCosa)
        dest.writeInt(valorPesos)
        dest.writeSerializable(serialnum)
        dest.writeSerializable(fechaCreacion)
        dest.writeString(idCosa)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR: Parcelable.Creator<Cosa>{
        override fun createFromParcel(source: Parcel): Cosa {
            Log.d(TAG, "create from parcel")
            return Cosa(source)
        }

        override fun newArray(size: Int): Array<Cosa?> {
            return arrayOfNulls(size)
        }
    }

}