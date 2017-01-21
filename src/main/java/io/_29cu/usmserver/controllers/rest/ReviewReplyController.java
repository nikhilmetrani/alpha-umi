package io._29cu.usmserver.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io._29cu.usmserver.controllers.rest.resources.ReviewReplyResource;
import io._29cu.usmserver.controllers.rest.resources.ReviewResource;
import io._29cu.usmserver.controllers.rest.resources.assemblers.ReviewReplyResourceAssembler;
import io._29cu.usmserver.core.model.entities.Review;
import io._29cu.usmserver.core.model.entities.ReviewReply;
import io._29cu.usmserver.core.model.entities.User;
import io._29cu.usmserver.core.service.ReviewReplyService;
import io._29cu.usmserver.core.service.ReviewService;
import io._29cu.usmserver.core.service.UserService;
import io._29cu.usmserver.core.service.exception.ReviewReplyDoesNotExistException;

@Controller
@RequestMapping("/api/0/consumer/review/{reviewId}/reviewReply")
public class ReviewReplyController {
	@Autowired
    private UserService userService;
	@Autowired
	private ReviewReplyService reviewReplyService;
	@Autowired
    private ReviewService reviewService;

	/**
	 * Create review for review
	 * @param reviewId The id of the review to be reviewed
	 * @param reviewReplyResource The new review instance
	 * @return
	 * @see ReviewReplyResource
	 */
	@RequestMapping(path = "/create", method = RequestMethod.POST)
	public ResponseEntity<ReviewReplyResource> createReview(@PathVariable String reviewId,
			@RequestBody ReviewReplyResource reviewReplyResource) {
		User user = userService.findAuthenticatedUser();
		if (user == null)
			return new ResponseEntity<ReviewReplyResource>(HttpStatus.FORBIDDEN);

		ReviewReply reviewReply = reviewReplyResource.toEntity();
		Review review = reviewService.findReview(new Long(reviewId));
		if (review == null) {
			return new ResponseEntity<ReviewReplyResource>(HttpStatus.NOT_FOUND);
		}
		reviewReply = reviewReplyService.createReviewReply(reviewReply, review, user);
		ReviewReplyResource createdReviewResource = new ReviewReplyResourceAssembler().toResource(reviewReply);
		return new ResponseEntity<ReviewReplyResource>(createdReviewResource, HttpStatus.CREATED);
	}

	/**
	 * Remove review for review
	 * @param reviewReplyId The id of review to be removed
	 * @return
	 * @see ReviewReplyResource
	 */
	@RequestMapping(path = "/remove/{reviewReplyId}", method = RequestMethod.DELETE)
	public ResponseEntity<ReviewReplyResource> removeReviewReply(@PathVariable String reviewReplyId) {
		User user = userService.findAuthenticatedUser();
		if (user == null)
			return new ResponseEntity<ReviewReplyResource>(HttpStatus.FORBIDDEN);

		try {
			reviewReplyService.removeReviewReply(new Long(reviewReplyId));
		} catch (ReviewReplyDoesNotExistException e) {
			return new ResponseEntity<ReviewReplyResource>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ReviewReplyResource>(HttpStatus.OK);
	}

}
