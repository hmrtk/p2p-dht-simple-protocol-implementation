P2P Distributed Hash Table Simple Protocol Implementation
=========================================================

Summary
-------
In this project the P2P Distributed Hash Table application is implemented in Java. This program is
created using Object oriented design. Documentations are also provided in the code for a better un-
derstanding of how the application works. During development of this application, GIT source version
controlling system was used and the code is hosted on Github: git://github.com/hmrtk/p2p-dht-simple-
protocol-implementation.git .

Design
------
This program consists of 6 separate class objects as follow:
-peer
	Contains the main method, creates an instance of PeerNode, processes the arguments that are passed to
	it from the command line through a method in the PeerNode instance, and passes the PeerNode object to
	Serverthread class.
-ServerThread
	Implements the Server part of the application. This server side of the application is implemented using multi
	threading design. It further uses methods inside the PeerNode object to processes the messages received
	from client and reply to them.
-PeerNode
	Implements a peer node in the network, it has all the required properties that defined in the assignment. It
	uses two objects Request and Response and Settings. The client part of the application that interacts with
	the server is implemented int this class inside a method. This class also takes care of all the conditions in
	the protocol when a message is being sent and received.
-Request
	Simulates a message that is sent to a server. When server gets the message the message is translated into
	this object, that makes it easier to further process the message through the application.
-Response
	Response class is similar to Request class with the only difference that it simulates messages that are sent
	from server to the client and is processed through client section of application.
-Settings
	This class has properties that are used as setting in the program. As an example the protocol version is
	defined here and can be changed if necessary.


Installation
------------
This program was developed using Eclipse IDE. The compiled files are located in the /bin folder and source
files are located in the /src folder.
The peer.java file is the starting point of the application that creates a peer node in the network. The
switches that can be passed to this file are:

	-i n — the ID for this peer
	-h x — host name of the computer that this program is running on
	-p n — port on which this peer will listen for other peers
	-m n — the maximum ID value
	-r x — host name of another peer in the system
	-s n — port number the other peer in the system
	-f — present if this is the first host in the system
	
Here is how this application can run from a unix operating system:

For the first peer the -f switch is passed to indicate that this peer is the only peer in the network.
	
	java peer -i 15 -h ubuntu -p 2112 -m 32 -f

Examples for other peers:

	java peer -i 10 -h ubuntu -p 2113 -m 32 -r ubuntu -s 2112
	java peer -i 20 -h ubuntu -p 2114 -m 32 -r ubuntu -s 2112

The communications between each peer is logged in server.p2plog under the /bin folder.
To communicate with each node, the following command can be used:

	telnet hostname port

	i.e telnet ubuntu 2112
These are the messages that can be sent to any servers using telnet:

	ID — Determine the peer in the system that is responsible for a specific ID. i.e ID 3171a 3/1.0 0 13CRLF
	ADD — Add a string to be stored at the peer. i.e ADD 3171a 3/1.0 1CRLFwhat time is it?CRLF
	QUERY — Determine whether or not this peer is storing a specific string. i.e QUERY 3171a 3/1.0 1CRLFkumquatCRL
	PULL — Retrieve any strings at this host that lie at or above the ID value of the requesting peer. That
	peer is getting ready to take over those strings. i.e PULL 3171a 3/1.0 1 27CRLFhector.cs.dal.ca 3000CRLF
	NEXT — Return the information for the next peer in the system. i.e NEXT 3171a 3/1.0 0CRLF
	DONE — Indicate to a peer that the calling peer will now take responsibility for answering all queries about strings at or beyond the sending peer’s ID value. i.e DONE 3171a 3/1.0 0 27CRLF

All messages have the following basic format where < sp > represents a space:

	operation < sp > version < sp > number of following lines < sp > optional peer ID CRLF
	0 or more following lines, each ending with CRLF

All responses have the following message format:

	version < sp > operation < sp > number of lines < sp > response code < sp > response string CRLF
	0 or more following lines, each ending with CRLF
