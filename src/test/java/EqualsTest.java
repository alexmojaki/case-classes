import org.junit.Test;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

public class EqualsTest {

    abstract class FlexibleBase extends AbstractCaseClass implements FlexiblyEqual {
        String x = "";
        String y = "";

        @Override
        public Class equalsBaseClass() {
            return FlexibleBase.class;
        }

    }

    class FlexibleNormal extends FlexibleBase {

        @Override
        public boolean buildResult(ResultBuilder r) {
            return r.name("x") && r.value(x) ||
                    r.name("y") && r.value(y);
        }
    }

    class FlexibleReversed extends FlexibleBase {

        @Override
        public boolean buildResult(ResultBuilder r) {
            return r.name("y") && r.value(y) ||
                    r.name("x") && r.value(x);
        }

    }

    class FlexibleReversedExtended extends FlexibleReversed {

        String z = "";

        @Override
        public boolean buildResult(ResultBuilder r) {
            return super.buildResult(r) ||
                    r.name("z") && r.value(z);
        }
    }

    class Rigid extends AbstractCaseClass {
        String x = "";
        String y = "";

        @Override
        public boolean buildResult(ResultBuilder r) {
            return r.name("x") && r.value(x) ||
                    r.name("y") && r.value(y);
        }
    }

    class RigidExtended extends Rigid {

        String z = "";

        @Override
        public boolean buildResult(ResultBuilder r) {
            return super.buildResult(r) ||
                    r.name("z") && r.value(z);
        }
    }

    interface Interface1 {
    }

    interface Interface2 {
    }

    class FlexibleWithInterface1 extends FlexibleNormal implements Interface1 {

        @Override
        public Class equalsBaseClass() {
            return Interface1.class;
        }
    }

    class FlexibleWithInterface2 extends FlexibleNormal implements Interface2 {

        @Override
        public Class equalsBaseClass() {
            return Interface2.class;
        }
    }

    class FlexibleWithBothInterfaces extends FlexibleNormal implements Interface1, Interface2 {

        @Override
        public Class equalsBaseClass() {
            return Interface1.class;
        }
    }

    @Test
    public void equivalenceClasses() {
        Utils.assertEquivalenceClasses(
                asList(new FlexibleNormal(), new FlexibleNormal(), new FlexibleNormal() {
                }, new FlexibleNormal() {
                }),
                asList(new FlexibleReversed(), new FlexibleReversed(), new FlexibleReversed() {
                }, new FlexibleReversed() {
                }),
                asList(new Rigid(), new Rigid()),
                singletonList(new Rigid() {
                }),
                singletonList(new Rigid() {
                }),
                asList(new FlexibleReversedExtended(), new FlexibleReversedExtended(), new FlexibleReversedExtended() {
                }, new FlexibleReversedExtended() {
                }),
                asList(new RigidExtended(), new RigidExtended()),
                singletonList(new RigidExtended() {
                }),
                singletonList(new RigidExtended() {
                }),
                asList(new FlexibleWithInterface1(), new FlexibleWithInterface1(), new FlexibleWithInterface1() {
                        }, new FlexibleWithInterface1() {
                        },
                        new FlexibleWithBothInterfaces(), new FlexibleWithBothInterfaces(), new FlexibleWithBothInterfaces() {
                        }, new FlexibleWithBothInterfaces() {
                        }
                ),
                asList(new FlexibleWithInterface2(), new FlexibleWithInterface2(), new FlexibleWithInterface2() {
                }, new FlexibleWithInterface2() {
                })
        );
    }

}
