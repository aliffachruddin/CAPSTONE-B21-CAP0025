package com.capstone.foodcy.data.csv

import android.app.Activity
import android.util.Log
import com.capstone.foodcy.data.entity.FoodEntity
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.io.*

class FoodcyCsv {

    fun getRecommendations(cluster : Int, activity : Activity?) : List<FoodEntity> {

        val fileName = "food_cluster.csv"

        val foods = ArrayList<FoodEntity>()

        if (activity != null) {
            val input = InputStreamReader(activity.application?.assets?.open(fileName))

            var fileReader : BufferedReader? = null
            var csvParser : CSVParser? = null
            try {
                fileReader = BufferedReader(input)
                csvParser = CSVParser(fileReader,
                    CSVFormat.DEFAULT
                        .withFirstRecordAsHeader()
                        .withIgnoreHeaderCase().withTrim())


                val csvRecords = csvParser.records

                for (csvRecord in  csvRecords) {
                    if (csvRecord.get(0) == cluster.toString()){
                        val food = FoodEntity(
                            csvRecord.get(0),
                            csvRecord.get(1),
                            csvRecord.get(2),
                            csvRecord.get(3),
                            csvRecord.get(4),
                            csvRecord.get(5),
                            csvRecord.get(6),
                            csvRecord.get(7),
                            csvRecord.get(8),
                            csvRecord.get(9),
                            csvRecord.get(11),
                            csvRecord.get(10),
                            csvRecord.get(12)
                        )

                        foods.add(food)
                    }
                }

            } catch (e: Exception) {
                Log.e("Reading CSV Error!", e.message.toString())
                e.printStackTrace()
            } finally {
                try {
                    fileReader!!.close()
                    csvParser!!.close()
                } catch (e: IOException) {
                    Log.e("Closing Error!", e.message.toString())
                    e.printStackTrace()
                }
            }
        }


        return foods
    }

    fun getFavorites(id: List<String>, activity : Activity?) : List<FoodEntity> {

        val fileName = "food_cluster.csv"

        val foods = ArrayList<FoodEntity>()

        if (activity != null) {
            val input = InputStreamReader(activity.application?.assets?.open(fileName))

            var fileReader : BufferedReader? = null
            var csvParser : CSVParser? = null
            try {
                fileReader = BufferedReader(input)
                csvParser = CSVParser(fileReader,
                    CSVFormat.DEFAULT
                        .withFirstRecordAsHeader()
                        .withIgnoreHeaderCase().withTrim())


                val csvRecords = csvParser.records

                for (foodId in id) {
                    for (csvRecord in  csvRecords) {
                        if (csvRecord.get(1) == foodId){
                            val food = FoodEntity(
                                csvRecord.get(0),
                                csvRecord.get(1),
                                csvRecord.get(2),
                                csvRecord.get(3),
                                csvRecord.get(4),
                                csvRecord.get(5),
                                csvRecord.get(6),
                                csvRecord.get(7),
                                csvRecord.get(8),
                                csvRecord.get(9),
                                csvRecord.get(11),
                                csvRecord.get(10),
                                csvRecord.get(12)
                            )

                            foods.add(food)
                        }
                    }
                }

            } catch (e: Exception) {
                Log.e("Reading CSV Error!", e.message.toString())
                e.printStackTrace()
            } finally {
                try {
                    fileReader!!.close()
                    csvParser!!.close()
                } catch (e: IOException) {
                    Log.e("Closing Error!", e.message.toString())
                    e.printStackTrace()
                }
            }
        }

        return foods
    }

    fun searchFood(foodsearch : String, activity : Activity?) : List<FoodEntity> {

        val fileName = "food_cluster.csv"

        val foods = ArrayList<FoodEntity>()

        if (activity != null) {
            val input = InputStreamReader(activity.application?.assets?.open(fileName))

            var fileReader : BufferedReader? = null
            var csvParser : CSVParser? = null
            try {
                fileReader = BufferedReader(input)
                csvParser = CSVParser(fileReader,
                    CSVFormat.DEFAULT
                        .withFirstRecordAsHeader()
                        .withIgnoreHeaderCase().withTrim())


                val csvRecords = csvParser.records

                    for (csvRecord in  csvRecords) {
                        if (csvRecord.get(2).lowercase().contains(foodsearch.lowercase())){
                            val food = FoodEntity(
                                csvRecord.get(0),
                                csvRecord.get(1),
                                csvRecord.get(2),
                                csvRecord.get(3),
                                csvRecord.get(4),
                                csvRecord.get(5),
                                csvRecord.get(6),
                                csvRecord.get(7),
                                csvRecord.get(8),
                                csvRecord.get(9),
                                csvRecord.get(11),
                                csvRecord.get(10),
                                csvRecord.get(12)
                            )

                            foods.add(food)
                        }
                    }

            } catch (e: Exception) {
                Log.e("Reading CSV Error!", e.message.toString())
                e.printStackTrace()
            } finally {
                try {
                    fileReader!!.close()
                    csvParser!!.close()
                } catch (e: IOException) {
                    Log.e("Closing Error!", e.message.toString())
                    e.printStackTrace()
                }
            }
        }

        return foods
    }

    fun getAllFood(activity: Activity?) : List<FoodEntity>{
        val fileName = "food_cluster.csv"

        val foods = ArrayList<FoodEntity>()

        if (activity != null) {
            val input = InputStreamReader(activity.application?.assets?.open(fileName))

            var fileReader : BufferedReader? = null
            var csvParser : CSVParser? = null
            try {
                fileReader = BufferedReader(input)
                csvParser = CSVParser(fileReader,
                    CSVFormat.DEFAULT
                        .withFirstRecordAsHeader()
                        .withIgnoreHeaderCase().withTrim())


                val csvRecords = csvParser.records

                for ((n,csvRecord) in csvRecords.withIndex()) {
                    val food = FoodEntity(
                        csvRecord.get(0),
                        csvRecord.get(1),
                        csvRecord.get(2),
                        csvRecord.get(3),
                        csvRecord.get(4),
                        csvRecord.get(5),
                        csvRecord.get(6),
                        csvRecord.get(7),
                        csvRecord.get(8),
                        csvRecord.get(9),
                        csvRecord.get(11),
                        csvRecord.get(10),
                        csvRecord.get(12)
                    )

                    foods.add(food)
                    if (n == 10) {
                        break
                    }
                }

            } catch (e: Exception) {
                Log.e("Reading CSV Error!", e.message.toString())
                e.printStackTrace()
            } finally {
                try {
                    fileReader!!.close()
                    csvParser!!.close()
                } catch (e: IOException) {
                    Log.e("Closing Error!", e.message.toString())
                    e.printStackTrace()
                }
            }
        }

        return foods
    }

}