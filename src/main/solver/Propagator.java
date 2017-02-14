package solver;

import definition.Constraint;
import definition.Csp;
import definition.Domain;
import definition.Variable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

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
    private boolean[] changedDomains;

    /**
     * Contient les contraintes que la propagation du filtrage va activer.
     * On active une contrainte quand le domaine d'une de ses variables change.
     */
    private Queue<Constraint> activeConstraints;


    Propagator(Csp csp, Variable currentNode) {
        this.csp = csp;
        this.currentNode = currentNode;
        this.savedDomains = csp.cloneDomains();
        this.activeConstraints = new LinkedList<Constraint>();
        this.arcConsistency = true;
    }


    // ------------------------------------------ Accessors ------------------------------------------------------------

    boolean areArcsConsistent() {
        return arcConsistency;
    }

    boolean[] changedDomains() {
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

    private boolean[] copyDomains() {
        return Arrays.copyOf(changedDomains, changedDomains.length);
    }

    private void setChangedDomains(boolean[] domains) {
        changedDomains = domains;
    }


    // ------------------------------------------ API ------------------------------------------------------------------

    /**
     * Restore les domaines à leur état d'avant le filtrage.
     */
    void restoreDomains() {
        int nbVars = csp.getNbVars();
        for (int i = 0; i < nbVars; i++)
            if (changedDomains[i])
                csp.getVars()[i].setDomain(savedDomains[i]);
    }

    /**
     * Lance le filtrage du Csp avec pour point de départ la variable var. Et effectue la
     * propagation à travers les contraintes qui concernet les variables dont le domaine
     * a été réduit par un filtrage.
     */
    void lauchPropagation() {
        prepareDomains();
        activeConstraints.addAll(csp.getConstraintsAsArrayList(currentNode));

        while (canStillPropagate() && arcConsistency) startPropagation();
    }


    // -----------------------------------------------------------------------------------------------------------------

    private void prepareDomains() {
        int nb = csp.getNbVars();
        changedDomains = new boolean[nb];
        for (int i = 0; i < nb; i++) changedDomains[i] = false;
    }

    private void startPropagation() {
        setCurrentConstraint(activeConstraints.poll());
        setCurrentFilter(currentConstraint.filter());

        if (currentFilter[0]) arcConsistency = false;
        else propagate();
    }

    private void propagate() {
        int len = currentFilter.length;
        for (int i = 1; i < len; i++)
            if (currentFilter[i])
                activateVariable(currentConstraint.getVars()[i - 1]);
    }

    private void activateVariable(Variable var) {
        changedDomains[var.getInd()] = true;
        addActivatedConstraints(var);
    }

    private void addActivatedConstraints(Variable modifiedVariable) {
        ArrayList<Constraint> cons1 = csp.getConstraintsAsArrayList(modifiedVariable);
        for (Constraint c1 : cons1)
            if (!c1.equals(currentConstraint) && !activeConstraints.contains(c1))
                activeConstraints.add(c1);
    }
}
