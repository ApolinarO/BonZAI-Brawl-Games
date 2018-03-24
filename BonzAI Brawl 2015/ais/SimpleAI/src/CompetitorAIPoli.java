package competitor;
import snowbound.api.*;
import snowbound.api.util.*;
import static snowbound.api.util.Utility.*;

import java.util.*;

@Agent(name = "AposAI")
public class CompetitorAI extends AI
{

	private Map<Unit, Base> goals;
	enum Personality {Runner, Aggressor, Defender};
	Personality personality;
	
	public CompetitorAI() 
	{
		this.goals = new HashMap<>();
	}
	
	public Action action(Turn turn) 
	{
		System.out.println("Hello World");
		Unit actor = turn.actor();
		
		//respans if actor must
		if(actor.position() == null) //spawns the person if they are not on the map 
			return spawn(turn);

		//if it is a runner
		if(personality == Personality.Runner)
			return runnerAction(turn);
		
		//if it is an aggressor
		//if(personality == Personality.Aggressor)
		//	return
		
		//self defense
		Unit enemy = any(actor.enemyUnitsInThrowRange());
		if(enemy != null && actor.snowballs() == 0)//if an enemy's with range and have a snowball, throw a snowball
			return new ThrowAction(enemy.position());
		/*
		if(tile.hasSnow())
		{
			if(actor.snowballs() < actor.statistic(Stat.CAPACITY))//if player is empty of snowballs get them
				return new GatherAction();
		}*/
		
		//dealing w/ goals
		/*if(goals.containsKey(actor) && actor.team().equals(turn.current(goals.get(actor)).owner()))//removes satisfied goals
				goals.remove(actor);
		
		if(!goals.containsKey(actor))//sets the actor's goal
		{
			//Base choice = any(difference(turn.allBases(), turn.myBases()));
			Base choice = any(turn.enemyBases());
			if(choice != null)
				goals.put(actor, choice);
		}
		Base goal = goals.get(actor);
		if(goal != null)
		{
			//if(goal.position().equals(actor.position()))//if at the goal's position, caputre it
				//return new CaptureAction();

			//return new MoveAction(target);
			Position target = getNearestBasePosition(turn);//the nearest base's nearest position
			return new MoveAction(target);//moves to the nearest base
		}*/
		
		return null;
	}

	SpawnAction spawn(Turn turn)
	{
		personality = Personality.Runner;
		return new SpawnAction(any(turn.actor().team().spawns()), getPerk());
	}
	Action runnerAction(Turn turn)
	{
		Unit actor  = turn.actor();

		if(turn.hasBaseAt(actor.position()) && !turn.baseAt(actor.position()).isOwnedBy(actor.team()))//if player is on a bases that's not theirs, then it takes it
			return new CaptureAction();
		else//if not, it movces to an unocupied base
		{
			List<Position> path = Pathfinding.getPath(turn, 	actor.position(),	getNearestBasePosition(turn));//the nearest base's nearest position
			Position target = furthest( path, actor.position());
			return new MoveAction(target);//moves to the nearest base (furthest position)
		}
	}
	Position getNearestBasePosition(Turn turn)
	{
		Base nearestBase = nearest(difference(turn.allBases(), turn.myBases()), turn.actor());//nearest base
		
		ArrayList<Position> usedPositions = new ArrayList<Position>();//holds all the used positions
		usedPositions.add(turn.actor().position());
		
		for(Unit people: turn.actor().unitsInMoveRange())//gets the used positions
			usedPositions.add(people.position());
		
		Set<Position> nearestPlausiblePosition = difference(nearestBase.coverage(), usedPositions);//the usable positions
		
		return nearest (nearestPlausiblePosition, turn.actor());
	}
	Perk getPerk()//chooses which Perk to spawn with
	{//BUCKET, CLEATS, LAYERS, NONE, PITCHER
		switch(personality)
		{
			case Runner: return Perk.CLEATS;
			default: return Perk.BUCKET;
		}
	}
}