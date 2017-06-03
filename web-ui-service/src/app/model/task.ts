export interface Task {
	id                 : string,
	images_number      : number,
	videos_number      : number,
	done               : boolean,
	processing         : boolean
	date               : string,
	approximate_size   : number,
	progress           : string,
	recognized		   : [{}],
	suspicious		   : [Suspicious]
}

export interface Suspicious {
	image: string,
	wanted_transport: WantedTransport
}

export interface WantedTransport {
	body_number: string,
	chassis_number: string,
	color: string,
	department: string,
	model: string,
	number_plate: string
}