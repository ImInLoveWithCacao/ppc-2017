package definition;

public class ConstraintDiff extends Constraint {
	
	
	public ConstraintDiff (Variable v1, Variable v2){
		super(new Variable[]{v1, v2});
	}
	
	@Override
	public boolean isSatisfied() {
		Variable [] vars = getVars();
		if (areInstanciated()) return vars[0].getValue() != vars[1].getValue();
		return true;
	}

	@Override
	public boolean isNecessary() {
		return isSatisfied();
	}
	
	@Override
	public boolean[] filter(){
		boolean rep1 = false;
		boolean rep2 = false;
		Variable v1 = getVars()[0];
		Variable v2 = getVars()[1];
		Domain d1 = v1.getDomain();
		Domain d2 = v2.getDomain();
		if (v1.isInstantiated()) rep2 = d2.remove(v1.getValue()) ;
		else if (v2.isInstantiated()) rep1 = d1.remove(v2.getValue());
		return new boolean[]{(d1.size() == 0 || d2.size() == 0), rep1, rep2};
	}
	
	public boolean equals (Object o){
		return (o instanceof ConstraintDiff) 
				&& ((ConstraintDiff)o).getVars().equals(getVars());
	}

	public String toString(){
		Variable[] vars = getVars();
		return (vars[0].getName() + " != " + vars[1].getName());
	}
}
