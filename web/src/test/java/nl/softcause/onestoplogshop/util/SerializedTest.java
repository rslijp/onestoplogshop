package nl.softcause.onestoplogshop.util;


import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import nl.softcause.onestoplogshop.config.JacksonModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class SerializedTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp(){
        objectMapper=new ObjectMapper().registerModules(new JacksonModule());
    }

    @Test
    public void should_serialize_date() throws JsonProcessingException {
        Instant at = Instant.parse("2007-12-03T10:15:30.00Z");
        Subject s = new Subject();
        s.setDate(at);

        String raw = objectMapper.writeValueAsString(s);


        assertThat(raw, is("{\"date\":\"2007-12-03T10:15:30.000Z\"}"));
    }

    @Test
    public void should_serialize_date_is_null_safe() throws JsonProcessingException {
        Subject s = new Subject();
        s.setDate(null);

        String raw = objectMapper.writeValueAsString(s);
        s = objectMapper.readValue(raw, Subject.class);


        assertThat(s.getDate(), nullValue());
    }

    @Test
    public void should_deserialize_date() throws IOException {
        Instant at = Instant.parse("2007-12-03T10:15:30.00Z");


        Subject s = objectMapper.readValue("{\"date\":\"2007-12-03T10:15:30.000Z\"}", Subject.class);


        assertThat(s.getDate(), is(at));
    }


    @Test
    public void should_serialize_date_bug1() throws JsonProcessingException {
        Instant at = Instant.parse("2021-01-19T21:33:07.830Z");
        Subject s = new Subject();
        s.setDate(at);

        String raw = objectMapper.writeValueAsString(s);


        assertThat(raw, is("{\"date\":\"2021-01-19T21:33:07.830Z\"}"));
    }

    @Test
    public void should_deserialize_date_bug1() throws JsonProcessingException {
        Instant at = Instant.parse("2021-01-19T21:33:07.830Z");
        Subject s = new Subject();
        s.setDate(at);

        String raw = objectMapper.writeValueAsString(s);


        assertThat(raw, is("{\"date\":\"2021-01-19T21:33:07.830Z\"}"));
    }

    @Test
    public void should_serialize_and_deserialize_date_with_type_handling() throws JsonProcessingException {
        Instant at = Instant.parse("2007-12-03T10:15:30.00Z");
        var source = new Container();
        source.data=new HashMap<>();
        source.data.put("at", at);

        String raw = objectMapper.writeValueAsString(source);


        System.out.println(raw);
        assertThat(raw, is("{\"data\":{\"at\":{\"className\":\"java.time.Instant\",\"value\":\"2007-12-03T10:15:30.000Z\"}}}"));

        var reconstructed = objectMapper.readValue(raw, Container.class);
//
//
//        assertThat(source.getDate(), is(at));
    }

    static class Subject {
        private Instant date;

        public Instant getDate() {
            return date;
        }

        public void setDate(Instant date) {
            this.date = date;
        }
    }

    static class ExplicitSubject {
        @JsonSerialize(using = InstantSerializer.class)
        @JsonDeserialize(using = InstantDeserializer.class)
        private Instant date;

        public Instant getDate() {
            return date;
        }

        public void setDate(Instant date) {
            this.date = date;
        }
    }

    public static class Container {
        @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "className")
        public Map<String, Object> data;

        public Container(){}

        public Container(Map<String, Object> data){this.data=data;}

        public Map<String, Object> getData() {
            return data;
        }

        public void setData(Map<String, Object> data) {
            this.data = data;
        }
    }
}


