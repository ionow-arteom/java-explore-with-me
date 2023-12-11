CREATE TABLE IF NOT EXISTS users (
	id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
	name VARCHAR(250) NOT NULL,
	email VARCHAR(254) NOT NULL,

	CONSTRAINT pk_users PRIMARY KEY (id),
	CONSTRAINT uq_users UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS categories (
	id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
	name VARCHAR(100) NOT NULL,

	CONSTRAINT pk_categories PRIMARY KEY (id),
	CONSTRAINT uq_categories UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS locations (
	id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
	lat FLOAT NOT NULL,
	lon FLOAT NOT NULL,

	CONSTRAINT pk_locations PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS events (
	id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
	initiator_id BIGINT NOT NULL,
	category_id BIGINT NOT NULL,
	location_id BIGINT NOT NULL,
	title VARCHAR(120) NOT NULL,
	annotation VARCHAR(2000) NOT NULL,
	event_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
	paid BOOLEAN DEFAULT FALSE NOT NULL,
	description VARCHAR(7000) NOT NULL,
	confirmed_requests BIGINT,
	participant_limit BIGINT DEFAULT 0,
	request_moderation BOOLEAN DEFAULT FALSE,
	published_on TIMESTAMP WITHOUT TIME ZONE,
	created_on TIMESTAMP WITHOUT TIME ZONE,
	state VARCHAR(20),
	views BIGINT,

	CONSTRAINT pk_events PRIMARY KEY (id),
	CONSTRAINT fk_initiator_id FOREIGN KEY (initiator_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT fk_category_id FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT fk_location_id FOREIGN KEY (location_id) REFERENCES locations(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS requests (
	id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
	event_id BIGINT NOT NULL,
	requester_id BIGINT NOT NULL,
	status VARCHAR(30) NOT NULL,
	created TIMESTAMP WITHOUT TIME ZONE,

	CONSTRAINT pk_requests PRIMARY KEY (id),
	CONSTRAINT uq_requests UNIQUE(event_id, requester_id),
	CONSTRAINT fk_requester_id FOREIGN KEY (requester_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT fk_event_id FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS compilations (
	id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
	pinned BOOLEAN DEFAULT FALSE,
	title VARCHAR(50) NOT NULL,

	CONSTRAINT pk_compilations PRIMARY KEY (id),
	CONSTRAINT uq_compilations UNIQUE (title)
);

CREATE TABLE IF NOT EXISTS compilations_events (
	id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
	compilation_id BIGINT NOT NULL,
	event_id BIGINT NOT NULL,

	CONSTRAINT fk_compilations_events_compilation_id FOREIGN KEY (compilation_id) REFERENCES compilations(id),
	CONSTRAINT fk_compilations_events_event_id FOREIGN KEY (event_id) REFERENCES events(id)
);

CREATE TABLE IF NOT EXISTS subscriptions (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    subscriber_id BIGINT NOT NULL,
    subscribed_to_id BIGINT NOT NULL,
    created TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,

    CONSTRAINT pk_subscriptions PRIMARY KEY (id),
    CONSTRAINT fk_subscriber_id FOREIGN KEY (subscriber_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_subscribed_to_id FOREIGN KEY (subscribed_to_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT uq_subscriptions UNIQUE (subscriber_id, subscribed_to_id)
);