package com.application.sensorData

import com.application.sensorData.caseclasses.SensorData
import com.application.sensorData.service.SensorStatisticServiceImpl
import org.scalatest.Tag
import org.scalatest.funsuite.AnyFunSuite

import java.io.File
import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/**
 * Created by prgaonka on 29-10-2022
 */

class SensorStatisticServiceImplTest extends AnyFunSuite {

  // TO TEST IF FILES ARE NOT PRESENT AT LOCATION
  test("INCORRECT LOCATION WITH NO FILES") {
    assert (SensorStatisticServiceImpl.getFileList(new File("csvFiles1")) === List())
  }

  // TO TEST IF FILES ARE PRESENT AT LOCATION
  test("CORRECT LOCATION WITH FILES") {
    assert (SensorStatisticServiceImpl.getFileList(new File("csvFiles")) != List())
  }

  // TO TEST IF STATISTICS ARE CORRECT
  test("SENSOR DATA STATISTICS") {
    assert (SensorStatisticServiceImpl.getSensorStatisticsData(mutable.HashMap("s3" -> List("NaN"), "s1" -> List("98", "NaN", "10"),
      "s2" -> List("78", "80", "88"))) === ListBuffer(SensorData("s3","NaN","NaN","NaN"), SensorData("s1","10","98","54"), SensorData("s2","78","88","82")))
  }

}




