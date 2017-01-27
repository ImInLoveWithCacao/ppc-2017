package definition;

/**
 * Created by IntelliJ IDEA.
 * User: xlorca
 */
public interface IConstraint {

    // Retourne les variables de la contrainte
    public Variable[] getVars();

    // Retourne vrai ssi toutes les variables de la contrainte sont instanciees et la contrainte est verifiee
    public boolean isSatisfied();
        
    // Une condition necessaire a la satisfaction de la contrainte :
    // retourne vrai ssi il existe encore un tuple satisfaisant la contrainte
    public boolean isNecessary();

    public String toString();
}
