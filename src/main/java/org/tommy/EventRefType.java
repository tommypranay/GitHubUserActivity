package org.tommy;

/**
 * Defines the classification of GitHub events based on the definiteness of their reference (e.g., branch, tag)
 * and the action performed. Useful for grouping events and applying logic based on their nature.
 *
 *
 * - DEFINITE_REF_AND_ACTION: Both the ref and the action are clearly defined
 *          e.g.: PullRequestReviewEvent, Here the User Reviewed a pull request
 * - DEFINITE_REF_UNDEFINITE_ACTION: Ref is definite, but the action varies and is found in payload
 *          e.g.: DeleteEvent, Here the User Deleted a branch or tag
 * - UNDEFINITE_REF_DEFINITE_ACTION: Action is defined, but the ref can vary or be absent
 *          e.g.: IssuesEvent, Here the User Opened or closed an issue
 */

public enum EventRefType {
    DEFINITE_ACTION_DEFINITE_REF,
    DEFINITE_ACTION_UNDEFINITE_REF,
    UNDEFINITE_ACTION_DEFINITE_REF;
}
