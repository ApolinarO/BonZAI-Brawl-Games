import java.util.*;
import bonzai.*;
import bonzai.util.*;
import lazers.*;
import lazers.api.*;

// Discovery vs Points: 1: 32, 2: 16, 3:8, 4:4, 5:2, 6:1
// Very important to get the 1st one
// Maniac Zack likes to go for the easy points; whatever's immediate, it grabs 1st
// Make a list of no hit things
// Turn isFirstTurn
@Agent(name = "Maniac Zack")
public class PoliAI extends AI 
{
	boolean ImArtifacts = true; // If there are immediate targets are within range
	Rotatable emEnd; // Where the emitter ends
	//Collection<Positionable> NoFlyList; //Stores dead ends
	
	public Action action(Turn turn) 
	{
		// Collection LazersMap.Hittablefrom; .canHit(Rotatable, p)
		// RotateAction.getRoataion
		// Rotatable isRotatableBy isControlling isColliding getContollingTeams getOwner
		// Can do GetPointValue to choose the next one
		Team myTeam 		= turn.getMyTeam();
		Emitter myEmitter 	= turn.getUtil().getMyEmitter();
		 
		System.out.println(turn.getTurnsRemaining());
		// if emEnd is null, then sets it to myEmitter; if not, moves it to a random emitter
		if(turn.isFirstTurn())
				emEnd = myEmitter;
		//else
			//emEnd = getEnd(turn, myTeam, emEnd);
		
		// Getting the immediate targets
		if(ImArtifacts)
		{
			// Gets the immediate targets within range
			Collection<Target> imTargets = turn.getUtil().hittableTargets(emEnd);
			if(imTargets.size()==0) // If none were found, then moves on
				ImArtifacts = false;
			else // If targets were found, then sees which ones are worthwhile
			{
				// Purges imTargets that have already been hit
				Iterator<Target> imTargIt = imTargets.iterator();
				Object target = null;
				while(imTargIt.hasNext())
				{
					target = imTargIt.next();
					//if(((Target)target).isDiscoveredByTeam(myTeam))
						//return new RotateAction(emEnd, (Target)target);
					if(((Target)target).isDiscoveredByTeam(myTeam))
						imTargets.remove(target);
				}
				
				//If there are targets, then points to it
				if(imTargets.size() !=0)
					return new RotateAction(emEnd, Utility.any(imTargets));
				else 
					ImArtifacts = false;
			}
		}
		
		// Moves to a new, random repeater and resets ImArtifacts
		ImArtifacts = true;
		
		// Rotates the end piece towards any hittableRepeater w/outBeingOwnedByMyTeam
		Collection<Repeater> imEm = turn.getUtil().hittableRepeaters(emEnd);
		Iterator<Repeater> inEmOr = imEm.iterator();
		Object target = null;
		while(inEmOr.hasNext())
		{
			target = inEmOr.next();
			//if(!((Repeater)target).isControlling(myTeam))
				//return new RotateAction(emEnd, (Repeater)target);
			if(((Repeater)target).isControlling(myTeam))
				imEm.remove(target);
		}
		
		inEmOr = imEm.iterator();
		Rotatable emEndPrev = emEnd;
		emEnd = inEmOr.next();
		
		return new RotateAction(emEndPrev, emEnd);
		//return new RotateAction(emEnd, Utility.any(turn.getUtil().hittableRepeaters(emEnd)));
	}
	
	// Returns the end of the emitter
	private Rotatable getEnd(Turn turn, Team myTeam, Rotatable myEmitter) // Gets the end of a chain
	{
		boolean isAtEnd = false; //Is at end
		int i= 0; // Iterates to see if it has reached the end of the emitters
		Rotatable retval = myEmitter;
		
		while(!isAtEnd)
		{  // For each hittable emitter in retval
			i=0;
			Collection<Repeater> imEmitters = turn.getUtil().hittableRepeaters(myEmitter);
			Iterator<Repeater> itEm = imEmitters.iterator();
			Object em;
			while(itEm.hasNext())
			{
				i++;
				em = itEm.next();
				if(retval.isColliding((Positionable)em)!=null)
				{
					retval = (Rotatable)em;
					break;
				}
			}
			i++;
			isAtEnd = (i > imEmitters.size());// If did whole loop, then reached end of emittion		
		}
		if(myEmitter.equals(retval))
			System.out.println("M");
		return retval;
	}
}