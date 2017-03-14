package solver;

import definition.Constraint;
import definition.Csp;
import definition.Domain;
import definition.Variable;

import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.IntStream;

class Propagator {
    private Csp csp;
    private Variable currentNode;
    private Domain[] savedDomains;
    private Constraint currentConstraint;
    private boolean[] currentFilter;

    /**
     * Vaut true si l'un des domaines a été vidé.
     */
    private boolean arcConsistency;

    /**
     * Un tableau de nbVariables boolean. L'élément i vaut true ssi son domaine a
     * changé lors de la propagation.
     */
    private Boolean[] changedDomains;

    /**
     * Contient les contraintes que la propagation du filtrage va activer.
     * On active une contrainte quand le domaine d'une de ses variables change.
     */
    private Queue<Constraint> activeConstraints;


    Propagator(Csp csp, Variable currentNode) {
        this.csp = csp;
        this.currentNode = currentNode;
        savedDomains = csp.cloneDomains();
        activeConstraints = new LinkedList<>();
        arcConsistency = true;
    }


    boolean areArcsConsistent() {
        return arcConsistency;
    }

    Boolean[] changedDomains() {
        return changedDomains;
    }

    private boolean canStillPropagate() {
        return !activeConstraints.isEmpty();
    }

    private void setCurrentConstraint(Constraint cons) {
        currentConstraint = cons;
    }

    private void setCurrentFilter(boolean[] filter) {
        currentFilter = filter;
    }

    /**
     * Restore les domaines à leur état d'avant le filtrage.
     */
    void restoreDomains() {
        IntStream.range(0, csp.getNbVars()).forEach(
                i -> {
                    if (changedDomains[i])
                        csp.getVars()[i].setDomain(savedDomains[i]);
                }
        );
    }

    /**
     * Lance le filtrage du Csp avec pour point de départ la variable var. Et effectue la
     * propagation à travers les contraintes qui concernet les variables dont le domaine
     * a été réduit par un filtrage.
     */
    void propagateFromCurrentNode() {
        prepareDomains();
        activeConstraints.addAll(csp.getConstraintsAsArrayList(currentNode));

        while (canStillPropagate() && arcConsistency)
            startPropagation();
    }


    private void prepareDomains() {
        changedDomains = IntStream.range(0, csp.getNbVars()).mapToObj(i -> false).toArray(Boolean[]::new);
    }

    private void startPropagation() {
        setCurrentConstraint(activeConstraints.poll());
        setCurrentFilter(currentConstraint.filter());

        if (currentFilter[0]) arcConsistency = false;
        else propagate();
    }

    private void propagate() {
        IntStream.range(0, currentFilter.length).forEach(
                i -> {
                    if (currentFilter[i])
                        activateVariable(currentConstraint.getVars()[i - 1]);
                }
        );
    }

    private void activateVariable(Variable var) {
        changedDomains[var.getInd()] = true;
        addActivatedConstraints(var);
    }

    private void addActivatedConstraints(Variable modifiedVariable) {
        csp.getConstraintsAsArrayList(modifiedVariable).forEach(
                c -> {
                    if (!c.equals(currentConstraint) && !activeConstraints.contains(c))
                        activeConstraints.add(c);
                }
        );
    }
}
