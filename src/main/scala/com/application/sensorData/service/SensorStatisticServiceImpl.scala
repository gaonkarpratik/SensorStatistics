package com.application.sensorData.service

import com.application.sensorData.caseclasses.SensorData
import com.application.sensorData.utils.Constants.sensorDataMap
import java.io.File
import scala.collection.mutable.ListBuffer
import scala.util.Try

/**
 * Created by prgaonka on 28-10-2022
 */

object SensorStatisticServiceImpl extends {

  /**
   * FETCHING LIST OF FILES FROM GIVEN LOCATION
   *
   * @param filePath DIRECTORY LOCATION WHERE WE NEED TO SEARCH FILES
   * @return List[File] - RETURN LIST OF ALL CSV FILES IN THAT PATH
   */
  def getFileList(filePath:File): List[File] ={

    // CHECK IF FILE DIRECTORY EXIST OR NOT TO AVOID NULL POINTER EXCEPTION
    val fileNames = if (filePath.exists && filePath.isDirectory) {
      filePath.listFiles.filter(file => file.isFile && file.getName.contains(".csv")).toList
    } else {
      List[File]()
    }
    fileNames
  }

  /**
   * FETCHING FILES DATA FROM LIST OF FILES FROM GIVEN LOCATION
   *
   * @param filesList LIST OF FILES AT DIRECTORY LOCATION
   * @return mutable.Map[String, List[String] - RETURN ALL SENSOR DATA ACROSS FILES IN MAP
   */
  def getFilesData(filesList:List[File]):scala.collection.mutable.Map[String, List[String]] ={

    filesList.foreach(filesPath=>{
      val bufferedSource = io.Source.fromFile(filesPath)
      for {
        line <- bufferedSource.getLines().drop(1).toVector
        values = line.split(",").map(_.trim)
      } yield  {
        if (sensorDataMap.isDefinedAt(values(0))) {
          val existingValue = sensorDataMap(values(0))
          val updatedList = values(1) :: existingValue
          sensorDataMap.put(values(0), updatedList)
        } else {
          sensorDataMap.put(values(0), List(values(1)))
        }
      }
      bufferedSource.close
    })
    sensorDataMap
  }

  /**
   * FETCHING SENSOR STATISTICS FROM ALREADY CREATED DATA MAP
   *
   * @param sensorsData MAP OF ALL SENSORS DATA ACROSS ALL FILES AT DIRECTORY LOCATION
   * @return ListBuffer[SensorData] - RETURN STATISTICS OF ALL SENSOR DATA ACROSS FILES IN MAP
   */
  def getSensorStatisticsData(sensorsData:scala.collection.mutable.Map[String, List[String]]):ListBuffer[SensorData]={

    val statisticsDataList = new scala.collection.mutable.ListBuffer[SensorData]()

    sensorsData.map(x=>{
      val key = x._1
      val value = x._2.flatMap(_.toIntOption)

      val maxValue = Try(value.max).getOrElse("NaN")
      val minValue = Try(value.min).getOrElse("NaN")
      val averageValue = Try(value.sum / value.size).getOrElse("NaN")
      statisticsDataList.append(SensorData(key, minValue.toString, maxValue.toString, averageValue.toString))
    })
    statisticsDataList
  }
}
