# BonzAI Brawl Info

In this project there are 3 projects. Only the project BanzaiBrawl-master is working.

## How To Get It Running

Make sure that you have Eclipse installed. When running eclipse, import the /BanzaiBrawl-master directory into Eclipse.

Once imported, run the program by selecting _Run_ > _External Tools_ > _Ant Build_. Or you can select the greed arrow with the red box and selecting the ant build from there.

When the program is running, select any map on the left. Note that some maps allow for different amounts of players.

## What Is The Game?

The name of the game is to laser blast as many items as possible. Once these items have been hit with a lazer, they are gone for good. If you manage to hit one, that is points for your team. You get the laser to hit the items by repoisitoning the mirrors so that they reflect the light. The more mirrors you hit, the higher score you get for hitting the boxes.


## Programming

This is the part where you guys would know about as much as I do. You just need to access the documentation. If it is not available to you select the following: _Project_ > _Generate Javadoc_. By default, the documentation should be found under _/doc_.

Now that you have access to the documentation, go to the _/ais/PlayerAI/src_ subfolder then _(default package)_. Inside rests a file called _CompetitorAI.java_. This is where you will write your AI code in.


## Fight Against People's AI's

So now you that you may or may not created your own AI's you want to see how each other's AI's measures up to one another's. It's a fairly simple process to pit each other's AI's against one another's.

First, in the source code, look at the following line at the top:

	@Agent(name = "PlayerAI")

Change the name parameter to something custom. Then under _/bonzai2016/_ then _/ais/_ lies a list of jar files with "AI" in the name. _PlayerAI.jar_ contains your ai compiled.

Simply copy that file or copy someone else's jar file or copy your jar file onto that directory. Make sure that you compiled your program and gave the jar a unique name.


## Tips During The Competition

It's worth spending a couple of minutes getting familiar with the documentation. A lot of my time was spent doing trial and error and that has not gotten me far. Amongst the AI's, you can see the Poli AI, which works only once. :(

This one year, some guy managed to retrieve the source code from one of the pre-written AI jar files. So there's that.

## Original Repository: https://github.com/bawilder/BanzaiBrawl
