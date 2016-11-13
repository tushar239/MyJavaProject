package java8.functionalprograming;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/*
    As you can see,
     - all if conditions can be replaced by filter operation
     - nilList.size() can be replaced by count() operation
     - null check and nilList.size() > 0 can be replaced by Optional returning api findAny().ifPresent()
     - if null (means if findAny().ifPresent() returns false) then return some other value. To do that you can use findAny().orElseGet(supplier returing some value), orElseThrow(supplier throwing an exception)

 */
public class Java8StreamWithComplexObject {

    public static void main(String[] args) {
        System.out.println(".........Trying aggregation operations ..........");
        {
            System.out.println("Filtering incentives having make=Toyota");
            final List<Incentive> incentives = filterIncentivesForMake(createIncentives().getIncentives(), "Toyota");
            incentives.forEach((incentive) -> System.out.println(incentive));
        }
        System.out.println();
        {
            System.out.println("Filtering incentives having make=something");
            final List<Incentive> incentives = filterIncentivesForMake(createIncentives().getIncentives(), "something");
            incentives.forEach((incentive) -> System.out.println(incentive));
        }
        System.out.println();
        {
            System.out.println("Testing the power of Optional");
            Incentive incentive = filterIncentivesForMakeAndFindOneMatchingIncentive(createIncentives().getIncentives(), "something");
            System.out.println(incentive.isEmpty());
        }
        System.out.println();
        {
            System.out.println("Testing the power Predicates chaining with and/or");
            long count = filterIncentivesForNonMatchingMakeAndMatchingYear(createIncentives().getIncentives(), "Toyota", 2013);
            System.out.println(count);
        }
        System.out.println(".........Trying terminal operation reduce ...........");
        {
            System.out.println("Testing power of map and reduce, Calculating total discount from incentives of Make=Toyota");
            System.out.println(calculateTotalDiscount(createIncentives().getIncentives(), "Toyota"));
        }

        System.out.println(".........Trying Optional instead of null check.............");
        {
            System.out.println(filterIncentivesForYear_with_nullcheck_replacedby_optional(createIncentives().getIncentives(), 2013));
            System.out.println(filterIncentivesForYear_with_nullcheck_replacedby_optional(null, 2013)); // null
        }

    }

    public static List<Incentive> filterIncentivesForYear_with_nullcheck_replacedby_optional(List<Incentive> incentives, int expectedYear) {
/*
        Composing mappings and mapping compositions pg 92
           which one is better? (VERY IMPORTANT)

           composing mappings
           stream.map(price -> price+tax).map(taxedPrice -> taxedPrice+shippingCost)....
                            or
           mapping compositions (andThen/compose methods of Function is are called compositions)
           stream.map((price -> (price+tax)).andThen(taxedPrice_+shiipingCost)).....

           Later one is better for smaller number of mappings. Mostly, it is better to compose the functions instead of mappings.
           But remember, composition call one function's apply method from another function's apply method. So, if you have converted more than 6000 mappers to composed functions, then you may get stack overflow.

           Same concept applies to filters and any other streaming functions. Functional Interface composition(chaining) is better than composing(chaining) streaming methods.
           Predicate, Consumer and Supplier do not have this problem because their methods don't call one within another. So, it is safe to compose predicates for a filter instead of using multiple filters.
 */


        // one way
        //BiPredicate<Criterion, Integer> predicate = (criterion, eY) -> ((YearCriterion) criterion).getYear() == eY;

       /* return incentives.stream()
                .filter(incentive -> incentive != null)
                .filter(incentive -> {
                    List<Criterion> criteria = incentive.getCriteria();
                    return criteria.stream()
                            .filter(criterion -> criterion != null)
                            .filter(criterion -> criterion instanceof YearCriterion)
                            .filter((criterion) -> ((YearCriterion) criterion).getYear() == expectedYear)
                            .findAny().isPresent();

                })
                .collect(Collectors.toList());*/

        // better way - combining all filters on the nilList of criteria stream in one filter by chaining predicates
        /*return incentives.stream()
                .filter(incentive -> incentive != null)
                .filter(incentive -> {

                    List<Criterion> criteria = incentive.getCriteria();

                    Predicate<Criterion> nullCheck = criterion -> criterion != null;
                    Predicate<Criterion> instanceOfCheck = criterion -> criterion instanceof YearCriterion;
                    Predicate<Criterion> yearCheck = criterion -> ((YearCriterion) criterion).getYear() == expectedYear;

                    return criteria.stream().filter(nullCheck.and(instanceOfCheck).and(yearCheck)).findAny().isPresent();
                })
                .collect(Collectors.toList());*/

        // Replacing all null checks with Optional.ofNullable(...)
        // This is just an example. Optional does not give any advantage over replacing null checks as it is done below.
        // Optional class includes methods to explicitly deal with the cases where a value is present or absent.
        // However, the advantage compared to null references is that the Optional class forces you to think about the case when the value is not present. As a consequence, you can prevent unintended null pointer exceptions.

        /*
        // Trying to use filter method of Optional instead of converting Optional's value to a stream after null check.
        // Not sure --- it is not working. it is just returning all incentives.

        return Optional.ofNullable(incentives)
                .filter(incentives2 -> {
                    return incentives2.stream()
                            .filter(incentive -> {
                                return Optional.ofNullable(incentive.getCriteria())
                                        .filter(criteria2 -> {
                                            return criteria2.stream()
                                                    .filter(criterion -> {
                                                        return Optional.ofNullable(criterion)
                                                                .filter(criterion1 -> {
                                                                    return criterion1 instanceof YearCriterion && ((YearCriterion)criterion1).getYear() == expectedYear;
                                                                })
                                                                .isPresent();

                                                    })
                                                    .findAny().isPresent();
                                        })
                                        .isPresent();
                            })
                            .findAny().isPresent();
                })
                .orElse(null);
        */

        final Optional<List<Incentive>> incentives1 = Optional.ofNullable(incentives);
        if (incentives1.isPresent()) {
            return incentives1.get().stream()
                    .filter(incentive -> {
                        if (Optional.ofNullable(incentive.getCriteria()).isPresent()) {

                            /*
                                if (Optional.ofNullable(criterion).isPresent()) {
                                    return criterion instanceof YearCriterion && ((YearCriterion) criterion).getYear() == expectedYear;
                                }
                            */

                            // OR

                            /*
                                return incentive.getCriteria().stream()
                                    .filter(criterion -> {
                                        Predicate<Criterion> criterionNullCheck = criterion1 -> Optional.of(criterion1).isPresent();
                                        Predicate<Criterion> criterionInstanceCheck = criterion1 -> criterion instanceof YearCriterion;
                                        Predicate<Criterion> criterionYearCheck = criterion1 -> ((YearCriterion) criterion1).getYear() == expectedYear;

                                        return criterionNullCheck.and(criterionInstanceCheck).and(criterionYearCheck).test(criterion);
                                    })
                                    .findAny().isPresent();
                             */

                            // OR

                            /*
                            Predicate<Criterion> criterionNullCheck = criterion1 -> Optional.of(criterion1).isPresent();
                            Predicate<Criterion> criterionInstanceCheck = criterion1 -> criterion1 instanceof YearCriterion;
                            Predicate<Criterion> criterionYearCheck = criterion1 -> ((YearCriterion) criterion1).getYear() == expectedYear;

                            return incentive.getCriteria().stream()
                                    .filter(criterionNullCheck.and(criterionInstanceCheck).and(criterionYearCheck))
                                    .findAny().isPresent();
                            */

                            // OR
                            // Using Optional's filter for criterion

                            return incentive.getCriteria().stream()
                                    .filter(criterion -> Optional.ofNullable(criterion)
                                            .filter(criterion1 -> criterion1 instanceof YearCriterion && ((YearCriterion) criterion1).getYear() == expectedYear)
                                            .isPresent())
                                    .findAny().isPresent();
                        }
                        return false;

                    })
                    .collect(Collectors.toList());
        }
        return null;
    }


    public static List<Incentive> filterIncentivesForMake(List<Incentive> incentives, String expectedMake) {

        return incentives.stream()
                .filter(incentive -> incentive != null)
                .filter(getPredicateComparingMakeCriterion(expectedMake))
                .collect(Collectors.toList());

    }

    protected static Predicate<Incentive> getPredicateComparingMakeCriterion(String expectedMake) {
        // Ideally, functional interfaces can be chained, so you don't need to use multiple same stream function.
        // Predicate can be combined using and/or. It can be negated using negate()
        // Consumer can be chained using andThen
        // Function can be chained using consume/andThen
        // Try to chain function interfaces in one same stream function. See getPredicateComparingYearCriterion(...) method.
        return (incentive) ->
                incentive.getCriteria().stream()
                        .filter(criterion -> criterion != null)
                        .filter(criterion -> criterion instanceof MakeModelCriterion)
                        .filter(criterion -> expectedMake.equalsIgnoreCase(((MakeModelCriterion) criterion).getMake()))
                                //.collect(Collectors.toList());
                                //.count() > 0
                        .findAny().isPresent();
    }

    protected static Predicate<Incentive> getPredicateComparingYearCriterion(int expectedYear) {
        Predicate<Criterion> nullCheckingPredicate = criterion -> criterion != null;
        Predicate<Criterion> instanceCheckingPredicate = criterion -> criterion instanceof YearCriterion;
        Predicate<Criterion> yearCheckingPredicate = criterion -> expectedYear == ((YearCriterion) criterion).getYear();

        return (incentive) ->
                incentive.getCriteria().stream()
                        .filter(nullCheckingPredicate.and(instanceCheckingPredicate).and(yearCheckingPredicate))
                        .findAny().isPresent();
    }

    public static Incentive filterIncentivesForMakeAndFindOneMatchingIncentive(List<Incentive> incentives, String expectedMake) {
        return incentives.stream().filter(incentive -> incentive != null)
                .filter(getPredicateComparingMakeCriterion(expectedMake))
                .findAny()  // This returns Optional. you can invoke other streaming api on Optional, but they will return Optional only, not a stream.
                .orElseGet(() -> new Incentive());
    }

    public static long filterIncentivesForNonMatchingMakeAndMatchingYear(List<Incentive> incentives, String expectedMake, int expectedYear) {
        return incentives.stream().filter(incentive -> incentive != null)
                .filter(getPredicateComparingMakeCriterion(expectedMake).negate().and(getPredicateComparingYearCriterion(expectedYear))) // predicate.negate() Returns a predicate that represents the logical negation of this predicate.
                .count();

    }

    public static double calculateTotalDiscount(List<Incentive> incentives, String expectedMake) {
        return incentives.stream()
                .filter(incentive -> incentive != null)
                .filter(getPredicateComparingMakeCriterion(expectedMake))
                .mapToDouble((incentive) -> {
                    if (incentive.getIncentiveType() == IncentiveTypeEnum.DEDUCT) {
                        return incentive.getValue();
                    } else if (incentive.getIncentiveType() == IncentiveTypeEnum.ADD && incentive.getValue() > 0) {
                        return -1 * incentive.getValue();
                    }
                    return 0;
                })
                .reduce((value1, value2) -> value1 + value2)
                .getAsDouble();
    }

    protected static CompositeIncentives createIncentives() {
        CompositeIncentives incentives = new CompositeIncentives();
        incentives.addIncentive(new Incentive() {{
            addCriterion(new MakeModelCriterion("Toyota", "Prius"));
            addCriterion(new YearCriterion(2013));
            setIncentiveType(IncentiveTypeEnum.DEDUCT);
            setValue(1000);
        }});
        incentives.addIncentive(new Incentive() {{
            addCriterion(new MakeModelCriterion("Toyota", "Camry"));
            addCriterion(new YearCriterion(2014));
            setIncentiveType(IncentiveTypeEnum.DEDUCT);
            setValue(2000);
        }});
        incentives.addIncentive(new Incentive() {{
            addCriterion(new MakeModelCriterion("Toyota", "Carola"));
            addCriterion(new YearCriterion(2015));
            setIncentiveType(IncentiveTypeEnum.ADD);
            setValue(100);
        }});
        incentives.addIncentive(new Incentive() {{
            addCriterion(new MakeModelCriterion("Honda", "Civic"));
            addCriterion(new YearCriterion(2015));
            setIncentiveType(IncentiveTypeEnum.ADD);
            setValue(100);
        }});
        return incentives;
    }

    static class CompositeIncentives {
        List<Incentive> incentives = new ArrayList<>();

        public void addIncentive(Incentive incentive) {
            if (incentive != null) {
                incentives.add(incentive);
            }
        }

        public List<Incentive> getIncentives() {
            return incentives;
        }
    }

    enum IncentiveTypeEnum {
        DEDUCT, ADD;
    }

    static class Incentive {
        IncentiveTypeEnum incentiveType = IncentiveTypeEnum.DEDUCT;
        double value;
        List<Criterion> criteria = new ArrayList<>();

        public IncentiveTypeEnum getIncentiveType() {
            return incentiveType;
        }

        public void setIncentiveType(IncentiveTypeEnum incentiveType) {
            this.incentiveType = incentiveType;
        }

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }

        public void addCriterion(Criterion criterion) {
            if (criterion != null) {
                criteria.add(criterion);
            }
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        public void setCriteria(List<Criterion> criteria) {
            this.criteria = criteria;
        }

        public boolean isEmpty() {
            return criteria.stream().findAny().isPresent();
        }

        @Override
        public String toString() {
            return "Incentive{" +
                    "criteria=" + criteria +
                    '}';
        }
    }

    interface Criterion {

    }

    static class MakeModelCriterion implements Criterion {
        String make;
        String model;

        public MakeModelCriterion(String make, String model) {
            this.make = make;
            this.model = model;
        }

        public String getMake() {
            return make;
        }

        public void setMake(String make) {
            this.make = make;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        @Override
        public String toString() {
            return "MakeModelCriterion{" +
                    "make='" + make + '\'' +
                    ", model='" + model + '\'' +
                    '}';
        }
    }

    static class YearCriterion implements Criterion {
        int year;

        public YearCriterion(int year) {
            this.year = year;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        @Override
        public String toString() {
            return "YearCriterion{" +
                    "year=" + year +
                    '}';
        }
    }
}
