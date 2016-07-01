pub struct Oracle {
	x: u32,
	y: u32
}

impl Oracle {
	pub fn new((x, y): (u32, u32)) -> Oracle {
		Oracle {
			x: x,
			y: y
		}
	}

	pub fn question(&self, (x1, y1): (u32, u32), (x2, y2): (u32, u32)) -> bool {
		assert!(x2 >= x1 && y2 >= y1);
		self.x >= x1 && self.y >= y1 && self.x <= x2 && self.y <= y2
	}
}

pub fn find_treasure((x1, y1): (u32, u32), (x2, y2): (u32, u32), oracle: &Oracle) -> Option<(u32, u32)> {
	// First, search the correct column.
	if x1 < x2 {
		let x_mid = (x1 + x2) / 2;

		if let Some(pos_bounty) = find_treasure((x1, y1), (x_mid, y2), oracle) {
			Some(pos_bounty)
		}
		else {
			find_treasure((x_mid + 1, y1), (x2, y2), oracle)
		}
	}
	// Then search the correct row.
	else if y1 < y2 {
		let y_mid = (y1 + y2) / 2;

		if let Some(pos_bounty) = find_treasure((x1, y1), (x2, y_mid), oracle) {
			Some(pos_bounty)
		}
		else {
			find_treasure((x1, y_mid + 1), (x2, y2), oracle)
		}
	}
	else {
		assert_eq!(x1, x2);
		assert_eq!(y1, y2);

		// This should be the place. Otherwise the oracle took us for a ride..
		if oracle.question((x1, y1), (x2, y2)) {
			Some((x1, y1))
		}
		else {
			None
		}
	}
}


pub fn main() {
	// First, let's hide a nice treasure and tell only the oracle about it.
	let oracle = Oracle::new((42, 42));

	// Now it's time to send those bastards looking for it.
	match find_treasure((0, 0), (1000, 1000), &oracle) {
		Some((x, y)) => println!("Found treasure at [{},{}]", x, y),
		None => println!("Sadly, we couldn't find the treasure.. aaarrghh..")
	}
}
