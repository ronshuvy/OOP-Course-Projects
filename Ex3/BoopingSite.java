import oop.ex3.searchengine.*;
import java.util.*;

/**
 * BoopingSite class
 * @author ronshuvy
 */
public class BoopingSite {

	//----------------CLASS CONSTANTS----------------
	private static final double MIN_LATITUDE = -90;
	private static final double MAX_LATITUDE = 90;
  	private static final double MIN_LONGITUDE = -180;
  	private static final double MAX_LONGITUDE = 180;
	//----------------------------------------------

	/* Hotels dataset */
	private final String dataset;

	/**
	 * Constructor
	 * @param name Name of the hotels dataset
	 */
	public BoopingSite(String name){
		this.dataset = name;
	}

	/*
	 * @param city Name of a city
	 * @return An array of hotels located in the given city
	 */
	private ArrayList<Hotel> getHotelsInCity(Hotel[] hotels, String city){
		ArrayList<Hotel> hotelsInCity = new ArrayList<Hotel>();
		for (Hotel hotel : hotels)
			if (hotel.getCity().equals(city))
				hotelsInCity.add(hotel);

		return hotelsInCity;
	}

	/*
	 * Return true if a given location is valid, false otherwise.
	 */
	private boolean isLocationValid(double latitude, double longitude){
		return MIN_LATITUDE <= latitude && latitude <= MAX_LATITUDE &&
			   MIN_LONGITUDE <= longitude && longitude <= MAX_LONGITUDE;
	}


	/**
	 * This method returns an array of hotels located in the given city,
	 * sorted from the highest star-rating to the lowest.
	 * Hotels that have the same rating will be organized according to the alphabet order of the hotel’s
	 * (property) name.
	 * @param city Name of a city
	 * @return array of hotels
	 * In case there are no hotels in the given city, this method returns an empty array.
	 */
	public Hotel[] getHotelsInCityByRating(String city){
		Hotel[] hotels = HotelDataset.getHotels(dataset);

		// Group all the hotels located in the given city
		ArrayList<Hotel> hotelsInCity = getHotelsInCity(hotels, city);

		// Sort by parameters
		RatingCompare ratingCompare  = new RatingCompare();
		NameCompare nameCompare  = new NameCompare();
		Collections.sort(hotelsInCity, nameCompare); // Sort by property name
		Collections.sort(hotelsInCity, ratingCompare); // Sort by rating name

		return (Hotel[])hotelsInCity.toArray(new Hotel[hotelsInCity.size()]);
	}


	/**
	 * This method returns an array of hotels, sorted according to their Euclidean distance from the given
	 * geographic location, in ascending order. Hotels that are at the same distance from the given
	 * location are organized according to the number of points-of-interest for which they are close to (in
	 * a decreasing order).
	 * @param latitude Location parameter
	 * @param longitude Location parameter
	 * @return array of hotels
	 * In case of illegal input, this method returns an empty array.
	 */
	public Hotel[] getHotelsByProximity(double latitude, double longitude){
		if (!isLocationValid(latitude, longitude))
			return new Hotel[0];

		List<Hotel> hotels = new ArrayList<Hotel>();
		Collections.addAll(hotels, HotelDataset.getHotels(dataset));

		// Sort by parameters
		ProximityCompare proximityCompare  = new ProximityCompare(latitude, longitude);
		PointsOfInterestCompare poiCompare  = new PointsOfInterestCompare();
		Collections.sort(hotels, poiCompare); // Sort by points of interest
		Collections.sort(hotels, proximityCompare); // Sort by property name

		return (Hotel[])hotels.toArray(new Hotel[hotels.size()]);
	}


	/**
	 * This method returns an array of hotels in the given city, sorted according to their Euclidean distance
	 * from the given geographic location, in ascending order. Hotels that are at the same distance from the
	 * given location are organized according to the number of points-of-interest for which they are close to
	 * (in a decreasing order).
	 * @param city Name of a city
	 * @param latitude Location parameter
	 * @param longitude Location parameter
	 * @return array of hotels
	 * In case of illegal input, this method returns an empty array.
	 */
	public Hotel[] getHotelsInCityByProximity(String city, double latitude, double longitude){

		Hotel[] hotels = HotelDataset.getHotels(dataset);
		if (!isLocationValid(latitude, longitude))
			return new Hotel[0];

		// Group all the hotels located in the given city
		ArrayList<Hotel> hotelsInCity = getHotelsInCity(hotels, city);

		// Sort by parameters
		ProximityCompare proximityCompare  = new ProximityCompare(latitude, longitude);
		PointsOfInterestCompare poiCompare  = new PointsOfInterestCompare();
		Collections.sort(hotelsInCity, poiCompare); // Sort by points of interest
		Collections.sort(hotelsInCity, proximityCompare); // Sort by property name

		return (Hotel[])hotelsInCity.toArray(new Hotel[hotelsInCity.size()]);
	}
}


/** Class to compare hotels by ratings */
class RatingCompare implements Comparator<Hotel> {
	public int compare(Hotel h1, Hotel h2)
	{
		return h2.getStarRating() - h1.getStarRating();
	}
}

/** Class to compare hotels by name (alphabet order) */
class NameCompare implements Comparator<Hotel> {
	/** Compare method */
	public int compare(Hotel h1, Hotel h2)
	{
		return h1.getPropertyName().compareTo(h2.getPropertyName());
	}
}

/** Class to compare hotels by points of interest */
class PointsOfInterestCompare implements Comparator<Hotel> {
	/** Compare method */
	public int compare(Hotel h1, Hotel h2)
	{
		return h2.getNumPOI() - h1.getNumPOI();
	}
}

/** Class to compare hotels by proximity to a given location */
class ProximityCompare implements Comparator<Hotel> {

	double latitude;
	double longitude;

	/** Initialize parameters */
	ProximityCompare(double latitude, double longitude){
		this.latitude = latitude;
		this.longitude = longitude;
	}

	/** Compare method */
	public int compare(Hotel h1, Hotel h2)
	{
		double dist1 = Math.pow((h1.getLatitude() - latitude), 2) +
					   Math.pow((h1.getLongitude() - longitude), 2);
		double dist2 =
				Math.pow((h2.getLatitude() - latitude), 2) +
				Math.pow((h2.getLongitude() - longitude), 2);

		if (dist1 == dist2)
			return 0;

		return dist1 > dist2 ? 1 : -1;
	}
}


