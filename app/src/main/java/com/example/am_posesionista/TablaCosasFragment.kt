package com.example.am_posesionista

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "debug"
class TablaCosasFragment : Fragment(){
    private lateinit var cosaRecyclerView: RecyclerView
    private var adaptador: CosaAdapter? = null
    private var callbackInterfaz: InterfazTablaCosas? = null

    interface InterfazTablaCosas{
        fun onCosaSeleccionada(unaCosa: Cosa)
    }

    override fun onStart(){
        super.onStart()
        val barraActividad = activity as AppCompatActivity
        barraActividad.supportActionBar?.setTitle(R.string.app_name)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onAttach(context: Context){
        super.onAttach(context)
        callbackInterfaz = context as InterfazTablaCosas?
    }

    override fun onDetach() {
        super.onDetach()
        callbackInterfaz = null
    }

    private fun actualizaUI(){
        val inventario = tablaDeCosasViewModel.inventory
        val alt_inventory = tablaDeCosasViewModel.cat1
        adaptador = CosaAdapter(alt_inventory)
        cosaRecyclerView.adapter = adaptador
    }

    private val tablaDeCosasViewModel: TablaCosasViewModel by lazy{
        ViewModelProvider(this).get(TablaCosasViewModel::class.java)
    }

    companion object{
        /*fun nuevaInstancia(): TablaCosasFragment{
            return TablaCosasFragment()
        }*/
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.lista_cosas_fragment, container,false)
        cosaRecyclerView = view.findViewById(R.id.cosa_recycler_view) as RecyclerView
        // Tipo de layout
        cosaRecyclerView.layoutManager = LinearLayoutManager(context)
        gesturesListener()
        actualizaUI()

        return view
    }

    private fun gesturesListener(){
        val simpleCallback = object: ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.LEFT){

            // Draggable Vertical
            override fun onMove( recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,  target: RecyclerView.ViewHolder): Boolean {
                    val from = target.absoluteAdapterPosition
                    val dest = viewHolder.absoluteAdapterPosition
                    tablaDeCosasViewModel.dragCosa(from, dest)
                    recyclerView.adapter?.notifyItemMoved(from, dest)
                    return false
            }

            // Delete on swipe left
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val alertConfirm = AlertDialog.Builder(context)
                alertConfirm.setTitle("Se eliminará el elemento.")
                alertConfirm.setPositiveButton("Ok") { _, _ ->
                    tablaDeCosasViewModel.dropCosa(viewHolder.absoluteAdapterPosition)
                    cosaRecyclerView.adapter?.notifyItemRemoved(viewHolder.absoluteAdapterPosition)
                    Toast.makeText(context, "Eliminado", Toast.LENGTH_SHORT).show()
                }
                alertConfirm.setNegativeButton("Cancelar") { _, _ ->
                    cosaRecyclerView.adapter?.notifyItemChanged(viewHolder.absoluteAdapterPosition)
                    Toast.makeText(context, "Acción cancelada", Toast.LENGTH_SHORT).show()
                }
                alertConfirm.create()
                alertConfirm.show()
            }
        }
        // Callback and attach
        val touchHelper= ItemTouchHelper(simpleCallback)
        touchHelper.attachToRecyclerView(cosaRecyclerView)
    }


    private inner class CosaHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener{
        // Item View es propiedad que toma lo que recibe CosaHolder como parametro
        private val nombreTextView: TextView = itemView.findViewById(R.id.label_nombre)
        private val precioTextView :TextView = itemView.findViewById(R.id.label_precio)
        private val serialTextview: TextView = itemView.findViewById(R.id.label_serial)
        private lateinit var cosa: Cosa

        init { itemView.setOnClickListener(this) }

        @SuppressLint("SetTextI18n")
        fun binding(cosa:Cosa){
            this.cosa = cosa
            nombreTextView.text = cosa.nombreCosa
            precioTextView.text = "$" + cosa.valorPesos.toString()
            serialTextview.text = "Serie: " + cosa.serialnum

            // Set background accordingly
            setBackground(cosa)
        }

        fun setBackground(cosa: Cosa){
            val parent = precioTextView.parent as View

            if(cosa.valorPesos < 100){
                parent.setBackgroundColor(Color.parseColor("#d8f3dc"))
                precioTextView.setTextColor(Color.BLACK)
                nombreTextView.setTextColor(Color.BLACK)
                serialTextview.setTextColor(Color.BLACK)
            }else if (cosa.valorPesos < 200){
                parent.setBackgroundColor(Color.parseColor("#b7e4c7"))
                precioTextView.setTextColor(Color.BLACK)
                nombreTextView.setTextColor(Color.BLACK)
                serialTextview.setTextColor(Color.BLACK)
            }else if (cosa.valorPesos < 300){
                parent.setBackgroundColor(Color.parseColor("#95d5b2"))
                precioTextView.setTextColor(Color.BLACK)
                nombreTextView.setTextColor(Color.BLACK)
                serialTextview.setTextColor(Color.BLACK)
            }else if (cosa.valorPesos < 400 ){
                parent.setBackgroundColor(Color.parseColor("#74c69d"))
                precioTextView.setTextColor(Color.BLACK)
                nombreTextView.setTextColor(Color.BLACK)
                serialTextview.setTextColor(Color.BLACK)
            }else if (cosa.valorPesos < 500){
                parent.setBackgroundColor(Color.parseColor("#52b788"))
                precioTextView.setTextColor(Color.WHITE)
                nombreTextView.setTextColor(Color.WHITE)
                serialTextview.setTextColor(Color.WHITE)
            }else if (cosa.valorPesos < 600){
                parent.setBackgroundColor(Color.parseColor("#40916c"))
                precioTextView.setTextColor(Color.WHITE)
                nombreTextView.setTextColor(Color.WHITE)
                serialTextview.setTextColor(Color.WHITE)
            }else if (cosa.valorPesos < 700){
                parent.setBackgroundColor(Color.parseColor("#007f5f"))
                precioTextView.setTextColor(Color.WHITE)
                nombreTextView.setTextColor(Color.WHITE)
                serialTextview.setTextColor(Color.WHITE)
            }else if (cosa.valorPesos < 800){
                parent.setBackgroundColor(Color.parseColor("#2d6a4f"))
                precioTextView.setTextColor(Color.WHITE)
                nombreTextView.setTextColor(Color.WHITE)
                serialTextview.setTextColor(Color.WHITE)
            }else if (cosa.valorPesos < 900){
                parent.setBackgroundColor(Color.parseColor("#1b4332"))
                precioTextView.setTextColor(Color.WHITE)
                nombreTextView.setTextColor(Color.WHITE)
                serialTextview.setTextColor(Color.WHITE)
            }else {
                parent.setBackgroundColor(Color.parseColor("#081c15"))
                precioTextView.setTextColor(Color.WHITE)
                nombreTextView.setTextColor(Color.WHITE)
                serialTextview.setTextColor(Color.WHITE)
            }
        }

        override fun onClick(v: View?){
            callbackInterfaz?.onCosaSeleccionada(cosa)
        }
    }

    private inner class CosaAdapter(var inventario: List<Cosa>):RecyclerView.Adapter<CosaHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CosaHolder {
            // parent se recibe como parametro, tipo viewgroup
            val holder = layoutInflater.inflate(R.layout.cosa_layout, parent, false)

            // Attach to Root,
            return CosaHolder(holder)
        }

        // Size of inventory, by param
        override fun getItemCount(): Int { return inventario.size }

        override fun onBindViewHolder(holder: CosaHolder, position: Int) {
           val cosa = inventario[position]
          /*holder.apply {
               nombreTextView.text = cosa.nombreCosa
               precioTextView.text = "$ ${cosa.valorPesos.toString()}"
            }*/
            holder.binding(cosa)
        }
    }

    // Cuando se crea el menu de opciones
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_tabla_cosas, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.nueva_cosa -> {
                val nuevaCosa = Cosa()
                tablaDeCosasViewModel.addCosa(nuevaCosa)
                callbackInterfaz?.onCosaSeleccionada(nuevaCosa)
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
