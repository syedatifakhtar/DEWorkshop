import java.io.File

import scala.annotation.tailrec
import scala.util.Try


implicit class MapJoin(map1: Map[String,String]) {
  def join(map2: Map[String,String]): Map[String,List[String]] = {
    map1.map{
      case (k,v)=>
         (k,List(v) ++ List(map2.getOrElse(k, "")))
    }
  }
}

//THE IDEA OF FUNCTIONAL

//WHY IS THIS BAD?
var x = 20
x=x+1

//WHY IS THIS GOOD
val y = 20
val z = y+1


//Think in terms of recursions (Lesser for loops)
def factorial(x: Int): Int = {
  if(x==1) 1 else x*factorial(x-1)
}

factorial(3)
//BUT BUT - CALL STACK?

@tailrec
def factorial(x: Int, acc: Int): Int = {
  if(x==1) acc
  else factorial(x-1,x*acc)
}

factorial(3,1)

//But but my parameters have changed!!

def newFactorial(x: Int) = {

  def innFact(x: Int, acc: Int): Int = {
    if(x==1) acc
    else factorial(x-1,x*acc)
  }
  innFact(x,1)
}

//Wait...but i can do this in Java with a Class - Right!OK!

//Functions as first class

val squareFunction = {x1: Int=>x1*x1}
val cubeFunction = {x1: Int=>x1*x1*x1}

def factorialOfFunction(x:Int,fn: Int=>Int): Int  = {
  if(x==1) fn(x)
  else fn(x) * factorialOfFunction(x-1,fn)
}

factorialOfFunction(3,squareFunction)
factorialOfFunction(3,cubeFunction)

//Oh But JAVA has GSON and other functional libraries - ME DONT NEED SCALA!
/*
WE NEED TO SIT DOWN AND TALK!
 */

//Sequence Elements in Scala

val numbersArray = Array(1,2,3,4,5,6,7,8,9,10) //Mutable,duplicates,preserve order

val numbersList = List(1,2,3,4,5,6,7,8,9,10) //Immutable,duplicates,preserve order

val numbersSet = Set(1,2,3,4,5,6,7,8,9,10) // Immutable,no duplicates,no order

val tuple1 = (9,10) //Tuple
val tuple2 = (1,2,3,4,5,6,7,8,9,10) //Tuple

val tuple3 = 9->10 // Tuple - different syntax...hmmmmmmn?

val map = Map("Scala"->"Awesome","Java"->"Booooo")

val anotherMap = Map(("JVM","AWESOME"),("GC","BOO")) //Hmmmmmnnnnn???!?

val mySAARS = null

val mySAARSOption: Option[Int] = Some(1000)

//Why?
mySAARSOption.map(x=>x/1000)

numbersList.map(_ * 3)

numbersList.foreach(println)

numbersList.filter(_%2==0)

(1 to 3).foldLeft(1)((a,b)=>a*b)

List(1).foldLeft(1)((a,b)=>a*b)
List(1).reduce((a,b)=>a*b)

List(11,10,12).reduce(_*_)

(1 to 100).toList.take(2)

List(List(1,2),List(2,3)).flatten

map.join(anotherMap)


case class Car(numTires: Int) {
  def move(direction: String) = {
    print(direction)
  }
}

val myCar = Car(10)
myCar move "Left"


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

val trains = tryTrains.filter(_.isSuccess).map(_.get)



trains
  .filter(t=>t.thirdAc=="1" && t.from_station_name.toLowerCase.contains("delhi"))

trains
  .filter{
    t=>
      t.thirdAc=="1" && t.from_station_name.toLowerCase
    .contains("delhi")}
  .map(_.duration_m.toInt).reduce(_+_)


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
  }.filter(_.isSuccess).map(_.get)






