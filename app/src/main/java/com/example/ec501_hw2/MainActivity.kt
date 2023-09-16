package com.example.ec501_hw2

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.ec501_hw2.databinding.ActivityCalculatorBinding
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import java.lang.Math.sqrt

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCalculatorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val b1:Button   = findViewById(R.id.button_1)
        val b2:Button   = findViewById(R.id.button_2)
        val b3:Button   = findViewById(R.id.button_3)
        val b4:Button   = findViewById(R.id.button_4)
        val b5:Button   = findViewById(R.id.button_5)
        val b6:Button   = findViewById(R.id.button_6)
        val b7:Button   = findViewById(R.id.button_7)
        val b8:Button   = findViewById(R.id.button_8)
        val b9:Button   = findViewById(R.id.button_9)
        val b0:Button   = findViewById(R.id.button_0)
        val b_add:Button   = findViewById(R.id.button_add)
        val b_minus:Button   = findViewById(R.id.button_minus)
        val b_mul:Button   = findViewById(R.id.button_mul)
        val b_div:Button   = findViewById(R.id.button_div)
        val b_sqrt:Button   = findViewById(R.id.button_sqrt)
        val b_equal:Button   = findViewById(R.id.button_equal)
        val text_ans:TextView = findViewById(R.id.show_ans)
        val b_clear:Button = findViewById(R.id.button_clear)
        val b_dot:Button = findViewById(R.id.button_dot)


        var lastIsNum = true
        var nums=mutableListOf<Double>()
        var isFraction = false
        var FractionLocation = 0
        var lastOperator = ""

        fun ifOutputFraction():Boolean{
            if(nums.size==2) return !(nums[0]==nums[0].toInt().toDouble() && nums[1]==nums[1].toInt().toDouble())
            return if(nums.size==1) nums[0] != nums[0].toInt().toDouble()
            else true
        }

        fun num_fun(num:Int){
            if (lastIsNum==true){
                var temp=0.0
                if (!nums.isEmpty()) {
                    temp = nums.removeAt(nums.size - 1)
                }

                if (!isFraction){
                    temp=temp*10+num
                    nums.add(temp)
                    text_ans.text=temp.toInt().toString()
                }
                else {
                    FractionLocation+=1
                    temp=temp+num*Math.pow(0.1, FractionLocation.toDouble())
                    nums.add(temp)
                    text_ans.text=temp.toString()
                }
            } else {
                when (lastOperator) {
                    "add" -> {
                        if(nums.size==2){
                            nums[0]=text_ans.text.toString().toDouble()
                            nums[1]=num.toDouble()
                        } else {
                            nums.add(num.toDouble())
                        }
                    }
                    "minus" -> {
                        if(nums.size==2){
                            nums[0]=text_ans.text.toString().toDouble()
                            nums[1]=-num.toDouble()
                        } else {
                            nums.add(-num.toDouble())
                        }
                    }
                    "mul" -> {
                        if(nums.size==2){
                            nums[1]=nums[1]*num
                            if(nums[1]==nums[1].toInt().toDouble()){
                                text_ans.text=nums[1].toInt().toString()
                            }else{
                                text_ans.text=nums[1].toString()
                            }
                        } else if (nums.size==1){
                            nums[0]=nums[0]*num
                            if(nums[0]==nums[0].toInt().toDouble()){
                                text_ans.text=nums[0].toInt().toString()
                            }else{
                                text_ans.text=nums[0].toString()
                            }
                        }
                    }
                    "div" -> {
                        if(nums.size==2){
                            nums[1]=nums[1]/num
                            if(nums[1]==nums[1].toInt().toDouble()){
                                text_ans.text=nums[1].toInt().toString()
                            }else{
                                text_ans.text=nums[1].toString()
                            }
                        } else if (nums.size==1){
                            nums[0]=nums[0]/num
                            if(nums[0]==nums[0].toInt().toDouble()){
                                text_ans.text=nums[0].toInt().toString()
                            }else{
                                text_ans.text=nums[0].toString()
                            }
                        }
                    }
                }
            }
            lastIsNum=true
        }

        b_mul.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if(!nums.isEmpty()){
                    lastIsNum=false
                    lastOperator="mul"
                }
            }
        } as View.OnClickListener)

        b_div.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if(!nums.isEmpty()){
                    lastIsNum=false
                    lastOperator="div"
                }
            }
        } as View.OnClickListener)

        b_equal.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                isFraction = false
                FractionLocation = 0
                lastIsNum=false
                lastOperator="add"
                if(nums.size==2){
                    if(nums[0]+nums[1]==(nums[0]+nums[1]).toInt().toDouble()){
                        text_ans.text=(nums[0]+nums[1]).toInt().toString()
                        nums[0]=text_ans.text.toString().toDouble()
                        nums.removeAt(nums.size - 1)
                    }else{
                        text_ans.text=(nums[0]+nums[1]).toString()
                        nums[0]=text_ans.text.toString().toDouble()
                        nums.removeAt(nums.size - 1)
                    }
                }
            }
        } as View.OnClickListener)

        b_add.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                isFraction = false
                FractionLocation = 0
                lastIsNum=false
                lastOperator="add"
                if(nums.size==2){
                    if(nums[0]+nums[1]==(nums[0]+nums[1]).toInt().toDouble()){
                        text_ans.text=(nums[0]+nums[1]).toInt().toString()
                    }else{
                        text_ans.text=(nums[0]+nums[1]).toString()
                    }
                }
            }
        } as View.OnClickListener)

        b_minus.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                isFraction = false
                FractionLocation = 0
                lastIsNum=false
                lastOperator="minus"
                if(nums.size==2){
                    if(nums[0]+nums[1]==(nums[0]+nums[1]).toInt().toDouble()){
                        text_ans.text=(nums[0]+nums[1]).toInt().toString()
                    }else{
                        text_ans.text=(nums[0]+nums[1]).toString()
                    }
                }

            }
        } as View.OnClickListener)

        b_dot.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                isFraction=true
            }
        } as View.OnClickListener)

        b_clear.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                lastIsNum = true
                nums=mutableListOf<Double>()
                text_ans.text="0"
                isFraction = false
                FractionLocation = 0
                lastOperator=""
            }
        } as View.OnClickListener)


        b_sqrt.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                var temp=0.0
                if (!nums.isEmpty()) {
                    temp = nums.removeAt(nums.size - 1)
                }
                temp= sqrt(temp.toDouble())
                nums.add(temp)
                text_ans.text=temp.toString()
                Toast.makeText(this@MainActivity, "sqrt", Toast.LENGTH_SHORT).show()

            }
        } as View.OnClickListener)


        b1.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                num_fun(1)
                Toast.makeText(this@MainActivity, "1", Toast.LENGTH_SHORT).show()
                lastIsNum=true
            }
        } as View.OnClickListener)

        b2.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                num_fun(2)
                Toast.makeText(this@MainActivity, "2", Toast.LENGTH_SHORT).show()
                lastIsNum=true
            }
        } as View.OnClickListener)

        b3.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                num_fun(3)
                Toast.makeText(this@MainActivity, "3", Toast.LENGTH_SHORT).show()
                lastIsNum=true
            }
        } as View.OnClickListener)

        b4.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                num_fun(4)
                Toast.makeText(this@MainActivity, "4", Toast.LENGTH_SHORT).show()
                lastIsNum=true
            }
        } as View.OnClickListener)

        b5.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                num_fun(5)
                Toast.makeText(this@MainActivity, "5", Toast.LENGTH_SHORT).show()
                lastIsNum=true
            }
        } as View.OnClickListener)

        b6.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                num_fun(6)
                Toast.makeText(this@MainActivity, "6", Toast.LENGTH_SHORT).show()
                lastIsNum=true
            }
        } as View.OnClickListener)

        b7.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                num_fun(7)
                Toast.makeText(this@MainActivity, "7", Toast.LENGTH_SHORT).show()
                lastIsNum=true
            }
        } as View.OnClickListener)

        b8.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                num_fun(8)
                Toast.makeText(this@MainActivity, "8", Toast.LENGTH_SHORT).show()
                lastIsNum=true
            }
        } as View.OnClickListener)

        b9.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                num_fun(9)
                Toast.makeText(this@MainActivity, "9", Toast.LENGTH_SHORT).show()
                lastIsNum=true
            }
        } as View.OnClickListener)

        b0.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                num_fun(0)
                Toast.makeText(this@MainActivity, "0", Toast.LENGTH_SHORT).show()
                lastIsNum=true
            }
        } as View.OnClickListener)
    }
}