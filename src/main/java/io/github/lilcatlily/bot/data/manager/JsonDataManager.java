package io.github.lilcatlily.bot.data.manager;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;

import java.io.*;
import java.nio.file.*;
import java.util.function.Supplier;

public class JsonDataManager<T> implements DataManager<T> {

	private static final ObjectMapper mapper = new ObjectMapper()
	                .setVisibility(PropertyAccessor.FIELD, Visibility.ANY)
	                .enable(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature())
	                .enable(JsonReadFeature.ALLOW_MISSING_VALUES.mappedFeature());
	private final Path path;
	private final T data;
	private final Supplier<T> constructor;

	public JsonDataManager(Class<T> clazz, String file, Supplier<T> constructor) {
		this.path = Paths.get(file);
		this.constructor = constructor;
		if (!path.toFile().exists()) {
			createFile(file);
		}

		try {
			this.data = fromJson(FileIO.read(path), clazz);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	public JsonDataManager(Class<T> clazz, File file, Supplier<T> constructor) {
		this.path = file.getAbsoluteFile().toPath();
		this.constructor = constructor;
		if (!path.toFile().exists()) {
			createFile(file.getPath());
		}

		try {
			this.data = fromJson(FileIO.read(path), clazz);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	private void createFile(String name) {
		try {
			if (path.toFile().createNewFile()) {
				FileIO.write(path, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(constructor.get()));
			} else {
				System.err.println(String.format("Could not create file [%s] at [%s]", name, path.toFile().getAbsolutePath()));
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public void delete() {
		try {
			path.toFile().delete();
		} catch (Exception e) {
			e.printStackTrace();;
		}
	}

	@Override
	public T get() {
		return data;
	}

	@Override
	public void save() {
		try {
			mapper.writerWithDefaultPrettyPrinter().writeValue(path.toFile(), data);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public static <T> String toJson(T object) throws JsonProcessingException {
		return mapper.writeValueAsString(object);
	}

	public static <T> T fromJson(String json, Class<T> clazz) throws JsonProcessingException {
		return mapper.readValue(json, clazz);
	}

	public static <T> T fromJson(String json, TypeReference<T> type) throws JsonProcessingException {
		return mapper.readValue(json, type);
	}

	public static <T> T fromJson(String json, JavaType type) throws JsonProcessingException {
		return mapper.readValue(json, type);
	}
}
