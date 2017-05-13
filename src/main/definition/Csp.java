package definition;

import tools.Solution;

import java.util.*;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Stream;

import static definition.factories.ConstraintFactory.binaryConstraint;
import static definition.factories.VariableFactory.createOneVar;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;
import static java.util.stream.IntStream.range;

public class Csp {
    private List<Variable> vars;
    private List<Constraint> cons;
    private Map<Variable, Set<Constraint>> relatedConstraints;

    public Csp() {
        vars = new LinkedList<>();
        cons = new LinkedList<>();
        relatedConstraints = new HashMap<>();
    }

    public Csp(Variable[] vars, Constraint[] cons) {
        System.out.println("creating csp");
        this.vars = Arrays.stream(vars).collect(toList());
        this.cons = Arrays.stream(cons).collect(toList());
        this.relatedConstraints = streamVars()
            .collect(toMap(
                identity(),
                var -> streamConstraints().filter(c -> c.affects(var)).collect(toSet()))
            );
    }

    public Csp(Variable[] vars) {
        this(vars, new Constraint[]{});
    }

    public List<Variable> getVars() {
        return vars;
    }

    private List<Constraint> getConstraints() {
        return this.cons;
    }

    public Stream<Variable> streamVars() {
        return getVars().stream();
    }

    public Stream<Variable> streamUninstantiated() {
        return streamVars().filter(Variable::isNotInstantiated);
    }

    public Domain[] cloneDomains() {
        return streamVars().map(Variable::cloneDomain).toArray(Domain[]::new);
    }

    Stream<Constraint> streamConstraints() {
        return getConstraints().stream();
    }

    public boolean hasSolution() {
        return streamConstraints().allMatch(Constraint::isSatisfied);
    }

    public Stream<Constraint> getRelatedConstraints(Variable var) {
        return relatedConstraints.get(var).stream();
    }

    public String toString() {
        final StringBuilder s = new StringBuilder("---Variables : \n");
        streamVars().forEach(v -> s.append(v).append("\n"));
        s.append("---Contraintes \n");
        streamConstraints().forEach(c -> s.append(c).append("\n"));
        return s.toString();
    }

    public Solution solution() {
        return new Solution(streamVars());
    }

    public void addOneVariable(int minD, int maxD) {
        Variable var = createOneVar(vars.size(), minD, maxD);
        vars.add(var);
        relatedConstraints.put(var, new HashSet<>());
    }

    public void addVariables(int nbVars, int minD, int maxD) {
        range(0, nbVars).forEach(i -> this.addOneVariable(minD, maxD));
    }

    public void addBinaryConstraint(String s) {
        try {
            String[] split = s.split(" ");
            Variable v1 = vars.get(split[0].charAt(1) - 48);
            Variable v2 = vars.get(split[2].charAt(1) - 48);
            Constraint constraint = binaryConstraint(v1, split[1], v2);
            cons.add(constraint);
            relatedConstraints.get(v1.getInd()).add(constraint);
            relatedConstraints.get(v2.getInd()).add(constraint);
        } catch (PatternSyntaxException e) {
            throw new IllegalArgumentException("Binary constrant definition should match x[0-9]+ ?(<|>|!)?=? ?x[0-9]+");
        }
    }
}