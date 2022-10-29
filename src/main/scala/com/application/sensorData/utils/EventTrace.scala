package com.application.sensorData.utils

import org.slf4j.{Logger, LoggerFactory}

/**
 * Created by prgaonka on 28-10-2022
 */

object EventTrace {

  val logger: Logger = LoggerFactory.getLogger("logger")

  /**
   * THIS METHOD IS USED TO ADD LOGGERS IN A KEY=VALUE PAIR FOR EASY SEARCH
   *
   * @param log ALL LOGS IN KEY=VALUE PAIR WILL BE INPUT TO THIS METHOD
   * @return Unit
   */
  def logTransactions(log:String):Unit={
    logger.info(s"$log")
  }

}
