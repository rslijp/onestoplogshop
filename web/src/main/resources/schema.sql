DROP SEQUENCE LOGGING_EVENT_SEQ IF EXISTS;
DROP TABLE logging_event_exception IF EXISTS;
DROP TABLE logging_event_property IF EXISTS;
DROP TABLE logging_event IF EXISTS;

CREATE SEQUENCE LOGGING_EVENT_SEQ START WITH 1 INCREMENT BY 50;

CREATE TABLE logging_event (
                               timestmp BIGINT NOT NULL,
                               formatted_message LONGVARCHAR NOT NULL,
                               logger_name VARCHAR(256) NOT NULL,
                               level_string VARCHAR(256) NOT NULL,
                               thread_name VARCHAR(256),
                               reference_flag SMALLINT,
                               arg0 VARCHAR(256),
                               arg1 VARCHAR(256),
                               arg2 VARCHAR(256),
                               arg3 VARCHAR(256),
                               caller_filename VARCHAR(256),
                               caller_class VARCHAR(256),
                               caller_method VARCHAR(256),
                               caller_line CHAR(4),
                               event_id IDENTITY NOT NULL);

CREATE INDEX logevent_level ON logging_event (level_string);
CREATE INDEX logevent_timestamp ON logging_event (timestmp);

CREATE TABLE logging_event_property (
                                        event_id BIGINT NOT NULL,
                                        mapped_key  VARCHAR(254) NOT NULL,
                                        mapped_value LONGVARCHAR,
                                        PRIMARY KEY(event_id, mapped_key),
                                        FOREIGN KEY (event_id) REFERENCES logging_event(event_id) ON DELETE CASCADE);

CREATE INDEX logging_event_property_keys ON logging_event_property (mapped_key);
CREATE INDEX logging_event_property_values ON logging_event_property (mapped_key, mapped_value);

CREATE TABLE logging_event_exception (
                                         event_id BIGINT NOT NULL,
                                         i SMALLINT NOT NULL,
                                         trace_line VARCHAR(256) NOT NULL,
                                         PRIMARY KEY(event_id, i),
                                         FOREIGN KEY (event_id) REFERENCES logging_event(event_id) ON DELETE CASCADE);
