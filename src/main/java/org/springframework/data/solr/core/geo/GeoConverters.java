/*
 * Copyright 2012 - 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.solr.core.geo;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;

/**
 * @author Christoph Strobl
 */
public final class GeoConverters {

	/**
	 * Converts a {@link GeoLocation} to a solrReadable request parameter.
	 */
	@WritingConverter
	public enum GeoLocationToStringConverter implements Converter<GeoLocation, String> {
		INSTANCE;

		@Override
		public String convert(GeoLocation source) {
			if (source == null) {
				return null;
			}
			String formattedString = StringUtils.stripEnd(
					String.format(java.util.Locale.ENGLISH, "%f", source.getLatitude()), "0")
					+ ","
					+ StringUtils.stripEnd(String.format(java.util.Locale.ENGLISH, "%f", source.getLongitude()), "0");

			if (formattedString.endsWith(".")) {
				return formattedString.replace(".", ".0");
			}
			return formattedString;
		}
	}

	/**
	 * Converts comma separated string to {@link GeoLocation}
	 */
	@ReadingConverter
	public enum StringToGeoLocationConverter implements Converter<String, GeoLocation> {
		INSTANCE;

		@Override
		public GeoLocation convert(String source) {
			if (source == null) {
				return null;
			}

			String[] coordinates = source.split(",");
			return new GeoLocation(Double.parseDouble(coordinates[0]), Double.parseDouble(coordinates[1]));
		}

	}

	/**
	 * Converts a {@link Distance} to a solrReadable request parameter.
	 */
	@WritingConverter
	public enum DistanceToStringConverter implements Converter<Distance, String> {
		INSTANCE;

		@Override
		public String convert(Distance source) {
			if (source == null) {
				return null;
			}
			return String.format(java.util.Locale.ENGLISH, "%s", source.getNormalizedValue());
		}
	}

	/**
	 * Converts a {@link Point} to a solrReadable request parameter.
	 * 
	 * @since 1.1
	 */
	public enum PointToStringConverter implements Converter<Point, String> {
		INSTANCE;

		@Override
		public String convert(Point source) {
			if (source == null) {
				return null;
			}
			String formattedString = StringUtils.stripEnd(String.format(java.util.Locale.ENGLISH, "%f", source.getX()), "0")
					+ ","
					+ StringUtils.stripEnd(String.format(java.util.Locale.ENGLISH, "%f", source.getY()), "0")
					+ (source.getZ() != null ? ("," + StringUtils.stripEnd(
							String.format(java.util.Locale.ENGLISH, "%f", source.getZ()), "0")) : "");

			if (formattedString.endsWith(".")) {
				return formattedString.replace(".", "");
			}
			return formattedString;
		}

	}
}
