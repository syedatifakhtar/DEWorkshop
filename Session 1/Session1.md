#What is Big Data? Why Big Data?

### The 4 V's

- Volume -> Petabytes of data - Queries/Statistical Analysis/Batch Data - Analyze billions of taxi rides
- Velocity -> Streaming Applications - Twitter/Facebook trends every minute/Swarm health/Real time trading
- Variety -> Different data formats - Videos/Tweets/Facebook comments
- Veracity/Quality -> Reliability/life time of data/Context/Data cleaning

### Why Big Data?

* Huge amounts/sources/formats of data being generated.
* Modern hardware hasn't been able to keep up with this huge burst
* Trend towards vertical scaling rather than horizontal scaling.Traditional Databases can't keep up.
* There has also been a trend of moving from simple web apps to more complicated and interactive websites
* Metadata has evolved over the years and is now almost of equal importance as the data itself in some cases
* The rise of Data Analytics and Machine Learning over the years

### Some Examples

- 2016 US Elections - Big data and social media used heavily

Some interesting stats-:

https://medium.com/google-cloud/big-data-and-the-elections-2016-5bd53dda2315

And why it failed?

http://www.techrepublic.com/article/election-tech-lies-damned-lies-and-statistics/ - Data veracity

- Tesla

https://evannex.com/blogs/news/ai-and-autopilot-how-tesla-is-winning-the-race-to-achieve-vehicle-autonomy-infographic

https://www.inc.com/kevin-j-ryan/how-tesla-is-using-ai-to-make-self-driving-cars-smarter.html

- Google Maps

https://www.ncta.com/platform/broadband-internet/how-google-tracks-traffic/

- Trend Analysis
	
	Facebook + Twitter + Youtube

	https://trends.google.com/trends/

- Predicting Crop Yields

https://www.theverge.com/2016/8/4/12369494/descartes-artificial-intelligence-crop-predictions-usda

### I have a lot of data, Should i use Spark/Hadoop/ElasticSearch/XYZ Big Data tool?



Simply put?NO!

Some questions to ask before you make the move

- But i have huge volume, and all the cool people are using Big Data!! - Have you indexed your SQL Server?Tried sharding/partitioning/indexing your data?No?->Start here.Big data has its own set of challenges, if you are already on MySQL/Postgres/Mongo - Optimize!

- What benefit will you get out of moving your current application to big data? Are you on the cloud? Is your application a batch application where time does not matter? Can you just put it on S3 and compute it once in a while?

- Do you have data scientists in place and are they likely to run iterative ML programs/run complex queries on your data?

- Do you have a good DevOps team to make use of and optimize your Big Data applications


___

### Ye Old MapReduce

- Whats a MapReduce?

Paper by Google-:

https://static.googleusercontent.com/media/research.google.com/en//archive/mapreduce-osdi04.pdf

*MapReduce is a programming model and an associated
implementation for processing and generating large
data sets. Users specify a map function that processes a
key/value pair to generate a set of intermediate key/value
pairs, and a reduce function that merges all intermediate
values associated with the same intermediate key. Many
real world tasks are expressible in this model, as shown
in the paper.*

So what does that mean?

A Simple example-:

Lets suppose we were all math and computer science nerds and while the rest of the world is out there watching Game of Thrones, we were busy trying to save the world. To catch up - some of us get together and try to do a quick summary of all the Game Of Thrones episodes (Eeek, is that even possible.). One of the first points we could start out with is trying to understand who the important characters are in the TV Series.We have the Subtitles.

We also want to find out what are the most common set of words uttered throughout besides regular words.

A quick and dirty way of finding out who the important characters are would be to just do a word count on all the dialogues and see which character has the most dialogues and is the subject of most Dialogues-:

*Arya Stark: l'm not a boy!
Yoren: You're not a smart boy, is that what you're trying to say? Do you want to live, boy?! (starts cutting Arya’s hair as the screams and shouts from the crowd are heard above) North, boy, we're going north.*

How would we do it in a distributed fashion?

Well, we first Map all the words to lower case and then Map them with a 1

(Why cant we just write a simple java program with a simple HashMap and count all of them? - Because, its 7 seasons worth of data and a lot of dialogues-> Any regular Java program would just be too inefficient to do this by its own, also we have a couple of spare machines :) )

(Map Stage)

arya-> 1

arya-> 1

boy-> 1

boy-> 1

yoren-> 1

boy-> 1
...And so on

Remember this is distributed code, and we want this to run across nodes, so there maybe different instances of the same nodes across multiple machines!

We now have all numbers mapped to 1's - Yay!!Also, how boring! We haven't accomplished anything here!What should we do next?

(Reduce Stage)

We reduce these records with the word as the key! 

So all the words on the same machines get their counts added up!

Lets say we have a 2 machine cluster, our data set after the reduce running on each machine could look like the following->

Machine 1

arya->1

boy->2

Machine 2

boy->2

yoren->1

So how do we get the final count?We run one more shuffle and reduce step and get the final counts

So all data from Machine 1 is transferred to Machine 2 and an additional reduce is run,the final data now looks like the following-:

Machine 2:

boy->4

arya->2

yoren->1

Yay!!We did it!!

So that folks is what a Map Reduce is!We take down any complex computation, in our case the count of words and break it down into chunks of Map Reduce steps which can then be distributed across multiple machines.

We'll get more hands on with our GOT analysis, but just in a bit

###Apache Hadoop 2.0

Lets pick up one of the most popular frameworks/setups out there to do this analysis- Apache Hadoop.

The complete stacks looks a little bit like below-:

![alt text](https://imgur.com/6VlwfWn "Apache Hadoop 2.0")

We'll get to the specifics of each component in a while.For now we will just talk about FileStorage

Given that Big Data aims to solve problems with both Volume and Velocity, typical FileSystems do not quite match up and there are several of them now.But we'll talk only about the important 3 - 

1. Local FS- (What you use on your current systems)
2. HDFS - Hadoop Distribute File Storage
3. Amazon S3.

- HDFS provides a logical volume on top of existing physical local volumes across nodes.

- It does so with the help of NameNodes and DataNodes.

- NameNodes store pointers to File blocks on respective machines

- Replication of data is done to ensure reliability

###Resources/Containers/Nodes

Resource management on Hadoop is performed by [YARN](https://hadoop.apache.org/docs/current/hadoop-yarn/hadoop-yarn-site/YARN.html)

Resources from multiple machines are broken down into VCORES and Memory units

When a Map or Reduce job runs -> it has to request YARN to allocate resources to it.

These resources are allocated in the form of containers which are nothing but JVM machines on each of the individual physical machines in the cluster and are monitored by YARN

Both map and reduce steps have fixed max containers size that they cannot breach.By default this is set to 4GB and 1 VCORE.

Make sure that your containers are big enough to compute at least data for a single key and big enough to hold the output of a reduce job.

Data can be read either from HDFS or S3 in case we use Apache Hadoop.(Local FS can also be used, but thats a different story)

___

Word Count Exercise - Game of Thrones Analysis using MR












