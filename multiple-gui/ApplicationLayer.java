package Task201;

public abstract class ApplicationLayer {
protected abstract Object[] create(String petID,String preyID,PresentationLayer GUI,String petName,int arrIndex);
	
	public Object[] createPetPrey(String petID,String preyID,PresentationLayer GUI,String petName,int arrIndex) {
		return create(petID,preyID,GUI,petName,arrIndex);
	}
}
