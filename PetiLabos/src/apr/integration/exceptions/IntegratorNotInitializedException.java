package apr.integration.exceptions;

import apr.integration.util.LinearSystemIntegrator;

public class IntegratorNotInitializedException extends RuntimeException {

    public IntegratorNotInitializedException(Class<? extends LinearSystemIntegrator> integrator) {
        super("This instance of " + integrator.getSimpleName() + " has not been initialized yet");
    }
}
