package Task201;

import Task201.PresentationLayer;

public class PetPreyApp extends ApplicationLayer {
	protected Object[] create(String petID, String preyID, PresentationLayer GUI, String petName, int arrIndex) {
		Pet pet;
		Prey prey;
		if (petID == "BIRD") pet = (Pet) new Bird(GUI,petName,arrIndex);
		else if (petID == "CAT") pet = (Pet) new Cat(GUI,petName,arrIndex);
		else pet = null;
		if (preyID == "INSECT") prey = (Prey) new Fly(GUI);
		else if (preyID == "MOUSE") prey = (Prey) new Mouse(GUI, petName, arrIndex);
		else prey = null;
		Object[] petPrey = {pet,prey};
		return petPrey;
	}
}
