package solver;

import org.junit.jupiter.api.Test;

import static solver.Solver.BACKTRACK;
import static solver.Solver.WITHFILTER;
import static solver.TestUtils.*;


class Eval2016 {
    //---------------------QUESTION 1------------------------------------------------------

    @Test
    void question_1_n_egal_5() {
        assertQueston1(5, 107, BACKTRACK);
    }

    @Test
    void question_1_n_egal_6() {
        assertQueston1(6, 1468, WITHFILTER);
    }

    @Test
    void question1_n_egal_7() {
        assertQueston1(7, 11024, WITHFILTER);
    }

    //------------------QUESTION 2---------------------------------------

    @Test
    void question_2_1_n_egal_10() {
        assertQuestion21(10);
    }

    @Test
    void question_2_1_n_egal_100() {
        assertQuestion21(100);
    }

    @Test
    void question_2_1_n_egal_1000() {
        assertQuestion21(1000);
    }

    @Test
    void question_2_1_n_egal_10000() {
        assertQuestion21(10000);
    }

    @Test
    void question_2_2_n_egal_1000() {
        assertQuestion22(1000);
    }

    @Test
    void question_2_2_n_egal_10000() {
        assertQuestion22(10000);
    }

    @Test
    void question_2_2_n_egal_100000() {
        assertQuestion22(100000);
    }

    @Test
    void question_2_2_n_egal_1000000() {
        assertQuestion22(1000000);
    }

}