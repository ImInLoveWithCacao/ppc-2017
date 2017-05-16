package definition;

import tools.Solution;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static definition.factories.ConstraintFactory.binaryConstraint;
import static java.lang.Integer.parseInt;

public class Csp {
    private List<Variable> vars;
    private List<Constraint> cons;
    private Map<Variable, Set<Constraint>> relatedConstraints;

    public Csp(Variable[] vars) {
        this(vars, new Constraint[]{});
    }

    public Csp(Variable[] vars, Constraint[] cons) {
        this();
        addVariables(vars);
        addConstraints(cons);
    }

    public Csp() {
        vars = new ArrayList<>();
        cons = new ArrayList<>();
        relatedConstraints = new HashMap<>();
    }

    public void addVariables(Variable... newVars) throws IllegalArgumentException {
        Arrays.stream(newVars).forEach(newVar -> {
            try {
                relatedConstraints.get(newVar).size();
                throw new IllegalArgumentException(
                    "Variable of index"
                        .concat(" " + newVar.getInd())
                        .concat(" already exists in csp")
                );
            } catch (NullPointerException e) {
                vars.add(newVar);
                relatedConstraints.put(newVar, new HashSet<>());
            }
        });
    }

    public void addBinaryConstraints(String... constraints) {
        Arrays.stream(constraints).forEach(s -> {
            Pattern pattern = Pattern.compile("^x([0-9]+) ?([<>!]?=?) ?x([0-9]+)$");
            Matcher matcher = pattern.matcher(s);
            if (matcher.matches()) {
                try {
                    addConstraints(binaryConstraint(
                        vars.get(parseInt(matcher.group(1))),
                        matcher.group(2),
                        vars.get(parseInt(matcher.group(3)))
                    ));
                } catch (IndexOutOfBoundsException e) {
                    throw new IllegalArgumentException("Variable of index "
                        .concat(e.getLocalizedMessage())
                        .concat(" currently does not exist in csp"));
                }
            } else {
                throw new IllegalArgumentException("Binary constraint definition should match "
                    .concat(pattern.toString()));
            }
        });
    }

    void addConstraints(Constraint... constraints) {
        Arrays.stream(constraints).forEach(constraint -> {
            cons.add(constraint);
            constraint.streamVars()
                .map(this::getRelatedConstraints)
                .forEach(set -> set.add(constraint));
        });
    }

    public List<Variable> getVars() {
        return vars;
    }

    List<Constraint> getConstraints() {
        return this.cons;
    }

    private Stream<Variable> streamVars() {
        return getVars().stream();
    }

    public Stream<Variable> streamUninstantiated() {
        return streamVars().filter(Variable::isNotInstantiated);
    }

    public Domain[] cloneDomains() {
        return streamVars().map(Variable::cloneDomain).toArray(Domain[]::new);
    }

    private Stream<Constraint> streamConstraints() {
        return getConstraints().stream();
    }

    public boolean hasSolution() {
        return streamConstraints().allMatch(Constraint::isSatisfied);
    }

    Set<Constraint> getRelatedConstraints(Variable var) {
        try {
            Set<Constraint> constraints = relatedConstraints.get(var);
            constraints.isEmpty();
            return constraints;
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Variable of index".concat(" " + var.getInd()).concat(" is not in Csp"));
        }
    }

    public Stream<Constraint> streamRelatedConstraints(Variable var) {
        return getRelatedConstraints(var).stream();
    }

    public Solution solution() {
        return new Solution(streamVars());
    }

    public String toString() {
        final StringBuilder s = new StringBuilder("---Variables : \n");
        streamVars().forEach(v -> s.append(v).append("\n"));
        s.append("---Contraintes \n");
        streamConstraints().forEach(c -> s.append(c).append("\n"));
        return s.toString();
    }
}