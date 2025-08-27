package by.clevertec;

import by.clevertec.model.Animal;
import by.clevertec.model.Car;
import by.clevertec.model.Examination;
import by.clevertec.model.Flower;
import by.clevertec.model.House;
import by.clevertec.model.Person;
import by.clevertec.model.Student;
import by.clevertec.util.Util;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Comparator;
import java.util.Arrays;
import java.util.AbstractMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static by.clevertec.util.CarUtil.getCountry;

public class Main {

	public static void main(String[] args) {
		task1();
		task2();
		task3();
		task4();
		task5();
		task6();
		task7();
		task8();
		task9();
		task10();
		task11();
		task12();
		task13();
		task14();
		task15();
		task16();
		task17();
		task18();
		task19();
		task20();
		task21();
		task22();
	}

	public static void task1() {
		List<Animal> animals = Util.getAnimals();

		AtomicInteger index = new AtomicInteger(0);

		Map<Integer, List<Animal>> zoos = animals.stream()
			.filter(animal -> animal.getAge() >= 10 && animal.getAge() <= 20)
			.sorted(Comparator.comparingInt(Animal::getAge))
			.collect(Collectors.groupingBy(e -> index.getAndAdd(1) / 7));

		System.out.println(zoos.get(2));
	}

	public static void task2() {
		List<Animal> animals = Util.getAnimals();
		animals.stream()
			.filter(animal -> "Japanese".equals(animal.getOrigin()))
			.peek(animal -> animal.setBread(animal.getBread().toUpperCase()))
			.filter(animal -> "Female".equals(animal.getGender()))
			.flatMap(animal -> Arrays.stream(animal.getBread().split(", ")))
			.forEach(System.out::println);
	}

	public static void task3() {
		List<Animal> animals = Util.getAnimals();
		animals.stream()
			.filter(animal -> animal.getAge() > 30)
			.map(Animal::getOrigin)
			.distinct()
			.filter(origin -> origin.startsWith("A"))
			.forEach(System.out::println);
	}

	public static void task4() {
		List<Animal> animals = Util.getAnimals();
		long femaleCount = animals.stream()
			.filter(animal -> "Female".equals(animal.getGender()))
			.count();

		System.out.println("Females count = " + femaleCount);
	}

	public static void task5() {
		List<Animal> animals = Util.getAnimals();
		boolean isHungarianAnimalExist = animals.stream()
			.filter(animal -> animal.getAge() >= 20 && animal.getAge() <= 30)
			.anyMatch(animal -> "Hungarian".equals(animal.getOrigin()));

		System.out.println("Is hungarian animal exists? " + isHungarianAnimalExist);
	}

	public static void task6() {
		List<Animal> animals = Util.getAnimals();
		boolean isTraditionalAnimals = animals.stream()
			.allMatch(animal -> "Female".equals(animal.getGender()) || "Male".equals(animal.getGender()));

		System.out.println("Are they traditional animals? " + isTraditionalAnimals);
	}

	public static void task7() {
		List<Animal> animals = Util.getAnimals();
		boolean noAnimalFromOceania = animals.stream()
			.noneMatch(animal -> "Oceania".equals(animal.getOrigin()));

		System.out.println("No one animal from oceania? " + noAnimalFromOceania);
	}

	public static void task8() {
		List<Animal> animals = Util.getAnimals();
		animals.stream()
			.sorted(Comparator.comparing(Animal::getBread))
			.limit(100)
			.mapToInt(Animal::getAge)
			.max()
			.ifPresent(System.out::println);
	}

	public static void task9() {
		List<Animal> animals = Util.getAnimals();
		animals.stream()
			.map(Animal::getBread)
			.map(String::toCharArray)
			.mapToInt(Array::getLength)
			.min()
			.ifPresent(System.out::println);
	}

	public static void task10() {
		List<Animal> animals = Util.getAnimals();
		int sumOfAges = animals.stream()
			.mapToInt(Animal::getAge)
			.sum();

		System.out.println("Sum of ages = " + sumOfAges);
	}

	public static void task11() {
		List<Animal> animals = Util.getAnimals();
		animals.stream()
			.filter(animal -> "Indonesian".equals(animal.getOrigin()))
			.mapToInt(Animal::getAge)
			.average()
			.ifPresent(System.out::println);
	}

	public static void task12() {
		List<Person> persons = Util.getPersons();
		persons.stream()
			.filter(person -> "Male".equals(person.getGender()))
			.filter(person -> person.getDateOfBirth().isBefore(LocalDate.now().minusYears(18)))
			.sorted(Comparator.comparingInt(Person::getRecruitmentGroup))
			.limit(200)
			.forEach(System.out::println);
	}

	public static void task13() {
		List<House> houses = Util.getHouses();
		Stream.concat(
			houses.stream()
				.filter(house -> "Hospital".equals(house.getBuildingType()))
				.flatMap(house -> house.getPersonList().stream()),
			houses.stream()
				.filter(house -> !"Hospital".equals(house.getBuildingType()))
				.flatMap(house -> house.getPersonList().stream())
				.filter(person ->
					person.getDateOfBirth().isAfter(LocalDate.now().minusYears(18)) ||       //дети
						person.getDateOfBirth().isBefore(LocalDate.now().minusYears(65)))        //старики
		)
			.limit(500)
			.forEach(System.out::println);
	}

	public static void task14() {
		List<Car> cars = Util.getCars();
		final double PRICE_PER_TON = 7.14;

		double totalSum = cars.stream()
			.filter(car -> getCountry(car).isPresent())
			.collect(Collectors.groupingBy(
				car -> getCountry(car).orElseThrow(),
				Collectors.summingDouble(Car::getMass))
			)                                               //пара: страна - сумма веса всех машин страны
			.entrySet()
			.stream()
			.peek(entry -> entry.setValue(entry.getValue() / 1000 * PRICE_PER_TON))         //пара: страна - сумма в долларах за все машины
			.peek(entry -> System.out.printf("%s = %f\n", entry.getKey(), entry.getValue()))
			.mapToDouble(Map.Entry::getValue)
			.sum();

		System.out.println("totalSum = " + totalSum);
	}

	public static void task15() {
		List<Flower> flowers = Util.getFlowers();
		final double PRICE_PER_CUBE_OF_WATER = 1.39;
		final double NUMBER_OF_YEARS = 5;
		double totalPrice = flowers.stream()
			.sorted(
				Comparator.comparing(Flower::getOrigin).reversed()
					.thenComparingInt(Flower::getPrice)
					.thenComparing(
						Comparator.comparingDouble(
							Flower::getWaterConsumptionPerDay
						).reversed()
					)
			)
			.filter(flower -> flower.getCommonName().toLowerCase().contains("s") ||
				flower.getCommonName().toLowerCase().contains("c")
			)
			.filter(flower -> flower.isShadePreferred() &&
				flower.getFlowerVaseMaterial().stream()
					.anyMatch(material -> "Glass".equals(material) ||
						"Steel".equals(material) ||
						"Aluminum".equals(material)
					)
			)
			.mapToDouble(flower -> flower.getPrice() + PRICE_PER_CUBE_OF_WATER * flower.getWaterConsumptionPerDay() * 365 * NUMBER_OF_YEARS)
			.sum();

		System.out.println("totalPrice = " + totalPrice);
	}

	public static void task16() {
		List<Student> students = Util.getStudents();
		students.stream()
			.filter(student -> student.getAge() < 18)               //что-то не то с тестовыми данными, нет студентов младше 18
			.sorted(Comparator.comparing(Student::getSurname))
			.forEach(student -> System.out.printf("%s - %d\n", student.getSurname(), student.getAge()));
	}

	public static void task17() {
		List<Student> students = Util.getStudents();
		students.stream()
			.map(Student::getGroup)
			.distinct()
			.sorted()                       //для красоты
			.forEach(System.out::println);
	}

	public static void task18() {
		List<Student> students = Util.getStudents();
		students.stream()
			.collect(
				Collectors.groupingBy(
					Student::getFaculty,
					Collectors.averagingInt(Student::getAge)
				)
			)
			.entrySet()
			.stream()
			.sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
			.forEach(System.out::println);

	}

	public static void task19() {
		String targetGroup = "P-1";
		List<Student> students = Util.getStudents();
		List<Examination> examinations = Util.getExaminations();
		students.stream()
			.filter(student -> targetGroup.equals(student.getGroup()))
			.filter(student -> examinations.stream().anyMatch(
				examination -> examination.getStudentId() == student.getId() &&
					examination.getExam3() > 4)
			)
			.forEach(System.out::println);
	}

	public static void task20() {
		List<Student> students = Util.getStudents();
		List<Examination> examinations = Util.getExaminations();

		students.stream()
			.flatMap(student -> examinations.stream()
				.filter(examination -> examination.getStudentId() == student.getId())
				.map(examination ->
					new AbstractMap.SimpleEntry<>(
						student.getFaculty(),
						examination.getExam1()
					)
				)
			)
			.collect(
				Collectors.groupingBy(
					AbstractMap.SimpleEntry::getKey,
					Collectors.averagingInt(AbstractMap.SimpleEntry::getValue)
				)
			).entrySet()
			.stream()
			.max(Comparator.comparingDouble(Map.Entry::getValue))
			.ifPresent(System.out::println);
	}

	public static void task21() {
		List<Student> students = Util.getStudents();
		students.stream()
			.collect(
				Collectors.groupingBy(
					Student::getGroup,
					Collectors.counting())
			)
			.entrySet()
			.forEach(System.out::println);
	}

	public static void task22() {
		List<Student> students = Util.getStudents();
		students.stream()
			.collect(Collectors.groupingBy(
				Student::getFaculty,
				Collectors.mapping(Student::getAge, Collectors.minBy(Integer::compare))
			))
			.forEach(
				(faculty, age) -> age.ifPresent(
					minAge -> System.out.printf("%s - %d\n", faculty, minAge)
				)
			);
	}
}
