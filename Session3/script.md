import java.io.File

import scala.annotation.tailrec
import scala.util.Try



case class Train(thirdAc: String
                 , arrival: String
                 , from_station_code: String
                 , name: String
                 , zone: String
                 , chair_car: String
                 , first_class: String
                 , duration_m: String
                 , sleeper: String
                 , from_station_name: String
                 , number: String
                 , departure: String
                 , return_train: String
                 , to_station_code: String
                 , second_ac: String
                 , to_station_name: String
                 , duration_h: String
                 , type_of_train: String
                 , first_ac: String
                 , distance: String)


val tryTrains = scala.io.Source.fromFile(new File("/Users/syedatifakhtar/Downloads/uber-tlc-foil-response/railways/trains.csv"))
  .getLines().toList.map{
  s=>
    val field = s.split(",")
    Try{
      Train(
      field(0),
      field(1),
      field(2),
      field(3),
      field(4),
      field(5),
      field(6),
      field(7),
      field(8),
      field(9),
      field(10),
      field(11),
      field(12),
      field(13),
      field(14),
      field(15),
      field(16),
      field(17),
      field(18),
      field(19)
    )}
}

case class Schedule(arrival: String
                    ,day: String
                    ,departure: String
                    ,id: String
                    ,stationCode: String
                    ,stationName: String
                    ,trainName: String
                    ,trainNumber: String)
val schedules =
  scala
    .io
    .Source
    .fromFile(new File("/Users/syedatifakhtar/Downloads/uber-tlc-foil-response/railways/schedules/part-00000-c754bea2-5248-4865-a53a-c580790608d4.csv"))
    .getLines()
    .toList
    .tail
    .map{
    s =>
      val f = s.split(",")
      Try {
        Schedule(
          f(0),
          f(1),
          f(2),
          f(3),
          f(4),
          f(5),
          f(6),
          f(7))
      }
  }

implicit class MapJoin(map1: Map[String,String]) {
  def join(map2: Map[String,String]): Map[String,List[String]] = {
    map1.map{
      case (k,v)=>
         (k,List(v) ++ List(map2.getOrElse(k, "")))
    }
  }
}
