package com.freddie.deserializers;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.freddie.objects.Series;

public class SeriesDeserializer implements JsonDeserializer<Series>
{

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss-SS");
	private static final Logger log = Logger.getLogger(SeriesDeserializer.class);

	@Override
	public Series deserialize(JsonElement json, Type arg1, JsonDeserializationContext arg2) throws JsonParseException
	{
		Series series = new Series();
		JsonObject obj = (JsonObject) json;
		if (obj.has("seriess"))
		{
			JsonArray seriesCollection = (JsonArray) obj.get("seriess");
			if (seriesCollection.size() == 1)
			{
				JsonObject seriesObj = (JsonObject) seriesCollection.get(0);
				series.setId(seriesObj.get("id").getAsString());
				series.setTitle(seriesObj.get("title").getAsString());
				series.setFrequency(seriesObj.get("frequency").getAsString());
				series.setFrequencyShort(seriesObj.get("frequency_short").getAsString());
				series.setUnits(seriesObj.get("units").getAsString());
				series.setUnitsShort(seriesObj.get("units_short").getAsString());
				series.setNotes(seriesObj.get("notes").getAsString());
				series.setPopularity(seriesObj.get("popularity").getAsInt());
				series.setSeasonalAdjustment(seriesObj.get("seasonal_adjustment").getAsString());
				series.setSeasonalAdjustmentShort(seriesObj.get("seasonal_adjustment_short").getAsString());

				try
				{
					series.setLastUpdated(timestampFormat.parse(seriesObj.get("last_updated").getAsString()));
				}
				catch (ParseException e)
				{
					log.error("Gson Parse Error" + e.getMessage());
				}
				try
				{
					series.setRealtimeStart(sdf.parse(seriesObj.get("realtime_start").getAsString()));
				}
				catch (ParseException e)
				{
					log.error("Gson Parse Error" + e.getMessage());
				}

				try
				{
					series.setRealtimeEnd(sdf.parse(seriesObj.get("realtime_end").getAsString()));
				}
				catch (ParseException e)
				{
					log.error("Gson Parse Error" + e.getMessage());
				}
			}
			else
			{
				log.error("More than one series found during Deserialization.");
				return null;
			}
		}
		else
		{
			log.error("No series array found during Deserialization.");
			return null;
		}
		return series;
	}
}
