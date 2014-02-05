Freddie is a simple Java library that wraps access to the St. Louis Fed's FRED API

```
String apiKey = "51ff1f1844537b524db28bddc9de4d4c";
String seriesId = "M08175USM144NNBR";
Freddie freddie = new Freddie(apiKey);

/*
 * This series will not be populated with observation data
 */
Series series = freddie.getSeriesById(seriesId, false);
assert (series.getData() == null);

/*
 * This one will!
 */
Series seriesWithData = freddie.getSeriesById(seriesId, true);

for (Observation o : seriesWithData.getData())
{
	System.out.println(seriesWithData.getId() + " Date: " + o.getDate() + " ----- " + o.getValue());
}
```