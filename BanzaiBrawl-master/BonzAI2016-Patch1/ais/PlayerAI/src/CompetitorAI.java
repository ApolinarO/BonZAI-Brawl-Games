import java.util.*;
import bonzai.*;
import bonzai.util.*;
import lazers.*;
import lazers.api.*;

@Agent(name = "PlayerAI")
public class CompetitorAI extends AI {
	public Action action(Turn turn) {
		Team myTeam 		= turn.getMyTeam();
		Emitter myEmitter 	= turn.getUtil().getMyEmitter();
		
		//Get a collection of everything we're interested in
		Collection<Positionable> interest = new LinkedList<Positionable>();
		
		//Add all repeaters and targets to this list
		interest.addAll(turn.getAllRepeaters());
		interest.addAll(turn.getAllTargets());
		
		//Remove anything that we can't hit from our emitter
		interest = Utility.retain(interest, new CanHitPredicate(myEmitter));
		
		//Pick one of our remaining objects of interest
		Positionable target = Utility.any(interest);
		
		//Rotate our emitter to this object
		return new RotateAction(myEmitter, target);
	}
}