package by.clevertec.util;

import by.clevertec.model.Car;

import java.util.Optional;
import java.util.stream.Stream;

public class CarUtil {

	public static Optional<String> getCountry(Car car) {
		if (isTurkmenistan(car)) {
			return Optional.of("Turkmenistan");
		} else if (isUzbekistan(car)) {
			return Optional.of("Uzbekistan");
		} else if (isKazakhstan(car)) {
			return Optional.of("Kazakhstan");
		} else if (isKurdistan(car)) {
			return Optional.of("Kurdistan");
		} else if (isRussia(car)) {
			return Optional.of("Russia");
		} else if (isMongolia(car)) {
			return Optional.of("Mongolia");
		} else {
			return Optional.empty();
		}
	}

	private static boolean isTurkmenistan(Car car) {
		return "Jaguar".equals(car.getCarMake()) || "White".equals(car.getColor());
	}

	private static boolean isUzbekistan(Car car) {
		return !isTurkmenistan(car) && (
			car.getMass() < 1500 ||
				Stream.of("BMW", "Lexus", "Chrysler", "Toyota")
					.anyMatch(str -> str.equals(car.getCarMake())
					)
		);
	}

	private static boolean isKazakhstan(Car car) {
		return !isUzbekistan(car) && (
			(
				car.getMass() > 4000 &&
					"Black".equals(car.getColor())
			) ||
				Stream.of("Dodge", "GMC")
					.anyMatch(str -> str.equals(car.getCarMake())
					)
		);
	}

	private static boolean isKurdistan(Car car) {
		return !isKazakhstan(car) && (
			car.getReleaseYear() < 1982 ||
				Stream.of("Civic", "Cherokee")
					.anyMatch(str -> str.equals(car.getCarMake())
					)
		);
	}

	private static boolean isRussia(Car car) {
		return !isKurdistan(car) && (
			Stream.of("Yellow", "Red", "Green", "Blue")
				.noneMatch(str -> str.equals(car.getColor())
				) ||
				car.getPrice() > 40_000
		);
	}

	private static boolean isMongolia(Car car) {
		return !isRussia(car) && car.getVin().contains("59");
	}
}
