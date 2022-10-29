package com.application.sensorData

import com.application.sensorData.service.SensorStatisticServiceImpl
import com.application.sensorData.utils.EventTrace

import java.io.File

/**
 * Created by prgaonka on 28-10-2022
 */

object SensorStatisticApp {

  def main(args: Array[String]): Unit = {

    val directoryPath = args(0)
    //EventTrace.logTransactions(s"DIRECTORY_PATH=$directoryPath")

    val filePath = new File(directoryPath)

    val totalNumberOfFiles = filePath.listFiles.count(f => f.isFile)
    EventTrace.logTransactions(s"TOTAL_NUMBER_OF_FILES(INCLUDING OTHER THAN CSV)=$totalNumberOfFiles")

    //GET TOTAL NUMBER OF FILES
    val totalNumberOfCSVFiles = filePath.listFiles.count(f => f.isFile && f.getName.contains(".csv"))
    EventTrace.logTransactions(s"NUM OF PROCESSED FILES(ONLY CSV)=$totalNumberOfCSVFiles")

    //GET FILE NAMES
    val fileNames = SensorStatisticServiceImpl.getFileList(filePath)
    //EventTrace.logTransactions(s"FILE_NAMES=$fileNames")

    //GET ALL FILES DATA
    val allFilesData = SensorStatisticServiceImpl.getFilesData(fileNames)
    //EventTrace.logTransactions(s"ALL_FILES_DATA=$allFilesData")

    //NO OF TOTAL MEASUREMENTS FROM ALL FILES
    val noOfMeasurements = allFilesData.map(_._2.size).sum
    EventTrace.logTransactions(s"NUM OF PROCESSED MEASUREMENTS=$noOfMeasurements")

    //GET ALL SENSORS STATISTICS
    EventTrace.logTransactions(s"SENSORS WITH HIGHEST AVG HUMIDITY:")
    EventTrace.logTransactions(s"SESNOR-ID\tMIN\tAVG\tMAX")
    val allSensorsStatistics = SensorStatisticServiceImpl.getSensorStatisticsData(allFilesData)
    val sortedList = allSensorsStatistics.sortBy(_.`sensor-id`)
    sortedList.foreach(sensorData=>{
      EventTrace.logTransactions(s"${sensorData.`sensor-id`}\t\t\t${sensorData.min}\t${sensorData.average}\t${sensorData.max}")
    })

  }
}
